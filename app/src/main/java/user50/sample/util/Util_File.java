package user50.sample.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresPermission;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class Util_File {

    /**
     * 저장소 경로 반환.
     * 반환 예시
     * └ /storage/emulated/0
     * @param context {@link Context}
     * @param isGetExternalPath 외부 저장소 반환 여부
     * @return 저장소 경로
     */
    public static String getStoragePath(Context context, boolean isGetExternalPath) throws IOException{

        String storagePath = "";

        String internalPath = "";
        String externalPath = "";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){

            // /storage/저장소명/Android/data/패키지명 형식의 File 리스트 가져오기
            File[] packageDirs = context.getExternalFilesDirs("");

            // 우리나라는 드물지만 2개 이상의 저장소를 가진 디바이스가 있어
            // 해당 디바이스는 따로 처리가 필요 할것으로 판단되어 오류 처리 함.
            if (packageDirs.length > 2) {
                throw new IOException("Can not find path. Cause device has more than 2 storage.");
            }

            for (int i = 0; i < packageDirs.length; i++) {

                File packageDir = packageDirs[i];
                if (packageDir != null) {

                    String tmpPackagePath = packageDir.getPath();
                    String tmpStoragePath = tmpPackagePath.substring(0, tmpPackagePath.indexOf("/Android"));

                    if (tmpStoragePath.toLowerCase().contains("emulated")) {
                        internalPath = tmpStoragePath;
                    } else {
                        externalPath = tmpStoragePath;
                    }

                }

            }

            if(isGetExternalPath){

                if(externalPath != null && externalPath.length() > 0){
                    storagePath = externalPath;
                }else{
                    throw new IOException("Can not find path. Cause find storage path is null.");
                }

            }else{

                if(internalPath != null && internalPath.length() > 0){
                    storagePath = internalPath;
                }else{
                    throw new IOException("Can not find path. Cause find storage path is null.");
                }

            }

        }else{

            try {

                internalPath = Environment.getExternalStorageDirectory().getPath();

                File root = new File("/mnt");
                File[] rootDir = root.listFiles();
                for (File dir : rootDir) {

                    if (dir.canWrite()) {

                        String tmpPath = dir.getPath();
                        if (tmpPath.contains("legacy")){
                            continue;
                        }

                        if (!internalPath.equals(tmpPath)) {
                            externalPath = tmpPath;
                            break;
                        }

                    }

                }

            } catch (Exception e) {
                throw new IOException("Can not find path. Cause " + e.getMessage());
            }

            if(isGetExternalPath){

                if(externalPath != null && externalPath.length() > 0){
                    storagePath = externalPath;
                }else{
                    throw new IOException("Can not find path. Cause find storage path is null.");
                }

            }else{

                if(internalPath != null && internalPath.length() > 0){
                    storagePath = internalPath;
                }else{
                    throw new IOException("Can not find path. Cause find storage path is null.");
                }

            }

        }

        return storagePath;

    }

    /**
     * 파일 경로 문자열을 {@link Uri} 문자열로 변환하여 반환.
     * uri가 사용 되어지는데서 반환 값이 정상 작동 되는지 확인 필요.
     *
     * 반환 예시
     * └ /storage/0000-0000/Android/data => file:///storage/0000-0000/Android/data
     * @param pathStr 파일 경로 문자열
     * @return null : 파일 경로가 null인 경우, {@link Uri} 문자열 : 정상 반환
     */
    public static String transFilePathToUri(String pathStr){

        String result = null;

        if(pathStr == null || pathStr.length() <= 0){
            result = null;
        }else{

            File f = new File(pathStr);
            Uri uri = Uri.fromFile(f);
            result = uri.toString();

        }

        return result;

    }

    /**
     * {@link Uri} 문자열을 파일 경로 문자열로 변환하여 반환.
     * 반환 예시
     * └ file:///storage/0000-0000/Android/data => /storage/0000-0000/Android/data
     * @param uriStr {@link Uri} 문자열
     * @return null : {@link Uri} 문자열이 null인 경우, 파일 경로 문자열 : 정상 반환
     */
    public static String transFileUriToPath(String uriStr){

        String result = null;

        if(uriStr == null || uriStr.length() <= 0){
            result = null;
        }else{

            Uri uri = Uri.parse(uriStr);
            result = uri.getPath();

        }

        return result;

    }

    /**
     * 파일 확장자 반환.
     * @param file 파일 확장자를 포함한 파일
     * @return null : 파일이 null일 경우, 파일 확장자 : 정상 반환
     */
    public static String getFileExtension(String file) {

        if(file == null){
            return null;
        }

        return file.substring(file.lastIndexOf(".") + 1);

    }

    /**
     * 파일 확장자를 제외한 파일명 반환.
     * @param file 파일 확장자를 포함한 파일
     * @return null : 파일이 null일 경우, 파일명 : 정상 반환
     */
    public static String getFileName(String file) {

        if(file == null){
            return null;
        }

        String fileName = file.substring(0, file.lastIndexOf("."));

        return fileName.substring(fileName.lastIndexOf("/") + 1);

    }

    /**
     * 파일을 복사하여 붙여넣기.
     * @param copyPath 복사 경로
     * @param copyFileName 복사 파일명(확장자 포함)
     * @param pastePath 붙여넣기 경로
     * @param pasteFileName 붙여넣기 파일명(확장자 포함)
     * @param isOverWrite 붙여넣기 경로에 같은 파일명을 가진 파일이 있을 경우 덮어쓰기 여부
     * @param isDeleteCopyFile 복사 파일 지우기 여부
     * @return true : 성공, false : 실패
     */
    @RequiresPermission(allOf = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    })
    public static boolean fileCopyPaste(String copyPath, String copyFileName, String pastePath, String pasteFileName, boolean isOverWrite, boolean isDeleteCopyFile) throws IOException{

        if(copyPath == null || copyPath.length() <= 0 ||
                copyFileName == null || copyFileName.length() <= 0){
            throw new IllegalArgumentException("Copy path or file name is null.");
        }

        if(pastePath == null || pastePath.length() <= 0 ||
                pasteFileName == null || pasteFileName.length() <= 0){
            throw new IllegalArgumentException("Paste path or file name is null.");
        }

        boolean isSuccess = false;

        File copy = new File(copyPath, copyFileName);
        File paste = new File(pastePath, pasteFileName);
        if(!copy.exists()){ // 복사 할 파일의 존재 여부 확인
            throw new IOException("Target copy file is not exists.");
        }else{

            if(!copy.isFile()){ // 복사 할 파일이 파일이 맞는지 여부 확인
                throw new IOException("Target copy file is not file.");
            }

        }

        File pasteFolder = paste.getParentFile();
        if(!pasteFolder.exists()){ // 붙여넣기 할 경로 존재 여부 확인
            isSuccess = pasteFolder.mkdirs();
        }else{

            if(paste.exists()){ // 붙여넣기 할 경로에 같은 이름의 파일이 있는지 확인

                if(isOverWrite){ // 덮어쓰기 여부 확인
                    isSuccess = paste.delete();
                }else{

                    File[] pasteFolderFileList = pasteFolder.listFiles(new FileFilter() {

                        @Override
                        public boolean accept(File pathname) {
                            return pathname.isFile();
                        }

                    });

                    boolean hasReName = false;
                    int num = 1;
                    String noExtensionPasteFileName = pasteFileName.substring(0, pasteFileName.lastIndexOf("."));
                    String pasteFileExtension = pasteFileName.substring(pasteFileName.lastIndexOf("."));
                    String reName = noExtensionPasteFileName + " (" + String.valueOf(num++) + ")" + pasteFileExtension;
                    for(int i = 0; i < pasteFolderFileList.length; i++){

                        File pasteFolderFile = pasteFolderFileList[i];
                        if(reName.equals(pasteFolderFile.getName())){
                            hasReName = true;
                        }

                        if(i == (pasteFolderFileList.length - 1)){

                            if(hasReName){
                                hasReName = false;
                                reName = noExtensionPasteFileName + " (" + String.valueOf(num++) + ")" + pasteFileExtension;
                                i = -1;
                            }

                        }

                    }

                    paste = new File(pasteFolder, reName);

                    isSuccess = true;

                }

            }else{
                isSuccess = true;
            }

        }

        if(!isSuccess){
            throw new IOException("Failed create paste path.");
        }
        isSuccess = false;

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {

            bis = new BufferedInputStream(new FileInputStream(copy));
            bos = new BufferedOutputStream(new FileOutputStream(paste));

            byte[] readByte = new byte[4096];
            int data = 0;
            while((data = bis.read(readByte, 0, 4096)) != -1) {
                bos.write(readByte, 0, data);
            }
            bos.flush();

            if(isDeleteCopyFile){
                isSuccess = copy.delete();
            }else{
                isSuccess = true;
            }

        } catch (IOException e) {
            throw new IOException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {

            try{

                bis.close();

            }catch (Exception e){
                // ignore
            }

            try{

                bos.close();

            }catch (Exception e){
                // ignore
            }

        }

        return isSuccess;

    }

    /**
     * 단일 파일(500mb) 기준으로 테스트 결과
     * 기존 IO를 사용한 경우보다 NIO를 사용한게 3배 정도 빠름.
     *
     * nio를 이용하면 두배정도의 속도차이를 보인다고 함.
     * nio를 이용한 source에서 target으로의 파일 복사.
     *
     * @param source 복사할 파일명을 포함한 절대 경로
     * @param target 복사될 파일명을 포함한 절대경로
     */
    public void copy(String source, String target) {

        // 복사 대상이 되는 파일 생성
        File sourceFile = new File( source );
        // 채널 선언
        FileChannel fcin = null;
        FileChannel fcout = null;

        try {

            // 채널 생성
            fcin = new FileInputStream(sourceFile).getChannel();
            fcout = new FileOutputStream(target).getChannel();

            // 채널을 통한 스트림 전송
            long size = fcin.size();
            fcin.transferTo(0, size, fcout);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            //자원 해제
            try{

                fcout.close();

            }catch(IOException ioe){}


            try{

                fcin.close();

            }catch(IOException ioe){}

        }

    }

    /**
     * 파일을 잘라내기하여 붙여넣기.
     * @param cutPath 잘라내기 경로
     * @param cutFileName 잘라내기 파일명
     * @param pastePath 붙여넣기 경로
     * @param pasteFileName 붙여넣기 파일명
     * @return true : 성공, false : 실패
     */
    @RequiresPermission(allOf = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    })
    public static boolean fileCutPaste(String cutPath, String cutFileName, String pastePath, String pasteFileName, boolean isOverWrite) throws IOException{

        if(cutPath == null || cutPath.length() <= 0 ||
                cutFileName == null || cutFileName.length() <= 0){
            throw new IllegalArgumentException("Copy path or file name is null.");
        }

        if(pastePath == null || pastePath.length() <= 0 ||
                pasteFileName == null || pasteFileName.length() <= 0){
            throw new IllegalArgumentException("Paste path or file name is null.");
        }

        boolean isSuccess = false;

        File copy = new File(cutPath, cutFileName);
        File paste = new File(pastePath, pasteFileName);
        if(!copy.exists()){ // 복사 할 파일의 존재 여부 확인
            throw new IOException("Copy file is not exists.");
        }else{

            if(!copy.isFile()){ // 복사 할 파일이 파일이 맞는지 여부 확인
                throw new IOException("Copy file is not file.");
            }

        }

        File pasteFolder = paste.getParentFile();
        if(!pasteFolder.exists()){ // 붙여넣기 할 경로 존재 여부 확인
            isSuccess = pasteFolder.mkdirs();
        }else{

            if(paste.exists()){ // 붙여넣기 할 경로에 같은 이름의 파일이 있는지 확인

                if(isOverWrite){ // 덮어쓰기 여부 확인
                    isSuccess = paste.delete();
                }else{

                    File[] pasteFolderFileList = pasteFolder.listFiles(new FileFilter() {

                        @Override
                        public boolean accept(File pathname) {
                            return pathname.isFile();
                        }

                    });

                    boolean hasReName = false;
                    int num = 1;
                    String noExtensionPasteFileName = pasteFileName.substring(0, pasteFileName.lastIndexOf("."));
                    String pasteFileExtension = pasteFileName.substring(pasteFileName.lastIndexOf("."));
                    String reName = noExtensionPasteFileName + " (" + String.valueOf(num++) + ")" + pasteFileExtension;
                    for(int i = 0; i < pasteFolderFileList.length; i++){

                        File pasteFolderFile = pasteFolderFileList[i];
                        if(reName.equals(pasteFolderFile.getName())){
                            hasReName = true;
                        }

                        if(i == (pasteFolderFileList.length - 1)){

                            if(hasReName){
                                hasReName = false;
                                reName = noExtensionPasteFileName + " (" + String.valueOf(num++) + ")" + pasteFileExtension;
                                i = -1;
                            }

                        }

                    }

                    paste = new File(pasteFolder, reName);

                    isSuccess = true;

                }

            }else{
                isSuccess = true;
            }

        }

        if(!isSuccess){
            throw new IOException("Failed create paste path.");
        }
        isSuccess = false;

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {

            bis = new BufferedInputStream(new FileInputStream(copy));
            bos = new BufferedOutputStream(new FileOutputStream(paste));

            byte[] readByte = new byte[4096];
            int data = 0;
            while((data = bis.read(readByte, 0, 4096)) != -1) {
                bos.write(readByte, 0, data);
            }
            bos.flush();

        } catch (IOException e) {
            throw new IOException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {

            try{

                bis.close();

            }catch (Exception e){
                // ignore
            }

            try{

                bos.close();

            }catch (Exception e){
                // ignore
            }

            isSuccess = copy.delete();

        }

        return isSuccess;

    }

//	/**
//	 * 폴더를 복사하여 붙여넣기.
//	 * @param copyPath 잘라내기 경로
//	 * @param copyFolderName 잘라내기 폴더명
//	 * @param pastePath 붙여넣기 경로
//	 * @param pasteFolderName 붙여넣기 폴더명
//	 * @param isDeleteCopyFolder 잘라내기 폴더 지우기 여부
//	 * @return true : 성공, false : 실패
//	 */
//    @RequiresPermission(allOf = {
//            android.Manifest.permission.READ_EXTERNAL_STORAGE,
//            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//    })
//	public static boolean folderCopyPaste(String copyPath, String copyFolderName, String pastePath, String pasteFolderName, boolean isDeleteCopyFolder) throws IOException{
//
//        if(copyPath == null || copyPath.length() <= 0 ||
//                copyFolderName == null || copyFolderName.length() <= 0){
//            throw new IllegalArgumentException("Copy path or folder name is null.");
//        }
//
//        if(pastePath == null || pastePath.length() <= 0 ||
//                pasteFolderName == null || pasteFolderName.length() <= 0){
//            throw new IllegalArgumentException("Paste path or folder name is null.");
//        }
//
//        boolean isSuccess = false;
//
//        File copy = new File(copyPath, copyFolderName);
//        File paste = new File(pastePath, pasteFolderName);
//        if(!copy.exists()){
//            throw new IOException("Copy folder is not exists.");
//        }else{
//
//            if(!copy.isDirectory()){
//                throw new IOException("Copy folder is not folder.");
//            }
//
//        }
//
//        isSuccess = paste.mkdirs();
//        if(!isSuccess){
//            throw new IOException("Failed create paste path.");
//        }
//
//        // FIXME 요거 함수 하나로 안되넹 ...
//
//        // FIXME
//
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
//        try {
//
//            bis = new BufferedInputStream(new FileInputStream(copy));
//            bos = new BufferedOutputStream(new FileOutputStream(paste));
//
//            byte[] readByte = new byte[4096];
//            int data = 0;
//            while((data = bis.read(readByte)) != -1) {
//                bos.write(data);
//            }
//            bos.flush();
//
//            if(isDeleteCopyFile){
//                isSuccess = copy.delete();
//            }else{
//                isSuccess = true;
//            }
//
//        } catch (IOException e) {
//            throw new IOException(e);
//        } catch (Exception e) {
//            throw new IOException(e);
//        } finally {
//
//            try{
//
//                bis.close();
//
//            }catch (Exception e){
//                // ignore
//            }
//
//            try{
//
//                bos.close();
//
//            }catch (Exception e){
//                // ignore
//            }
//
//        }
//
//        return isSuccess;
//
//	}
//
//    private static void asdf(){
//
//        File[] copyList = copy.listFiles();
//        for (File copyFile : copyList) {
//
//            String paste23 = copyFile.getPath().substring(copy.getPath().length());
//            if(copyFile.isDirectory()){
//                File paste2 = new File(paste + paste23);
//                paste2.mkdirs();
//            }else{
//                fileCopyPaste(copyFile.getPath(), copyFile.getName(), ((paste + paste23).substring(copyFile.getName().length())), copyFile.getName());
//            }
//
//        }
//
//    }
//
//	/**
//	 * 폴더를 잘라내기하여 붙여넣기.
//	 * @param cutPath 잘라내기 경로
//	 * @param cutFolderName 잘라내기 폴더명
//	 * @param pastePath 붙여넣기 경로
//	 * @param pasteFolderName 붙여넣기 폴더명
//	 * @return true : 성공, false : 실패
//	 */
//    @RequiresPermission(allOf = {
//            android.Manifest.permission.READ_EXTERNAL_STORAGE,
//            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//    })
//	public static boolean folderCutPaste(String cutPath, String cutFolderName, String pastePath, String pasteFolderName){
//
//	}

    /**
     * 파일 또는 폴더 삭제 후 성공 여부 반환.
     * 폴더 삭제시 하위 파일이 없을 경우에만 폴더 삭제가 가능하기에
     * 메서드 정의.
     *
     * @param file 삭제 할 파일 또는 폴더
     * @return true : 삭제 성공, false : 삭제 실패
     */
    public static boolean delete(File file){

        if(file == null){
            return false;
        }

        if(file.isDirectory()){

            File[] childFiles = file.listFiles();
            for (File childFile : childFiles) {

                if(!delete(childFile)){
                    return false;
                }

            }

        }

        return file.delete();

    }

    /**
     * 폴더 크기 계산하여 반환.
     * 파일도 사용 가능하나
     * {@link File#length()}를 사용하는게 더 간단함.
     * @param folder 크기 계산할 폴더
     * @return 0 : 계산할 폴더가 null이거나 크기가 0인 경우, 그 외 : 계산 된 크기(byte)
     */
    public static long calculatefolderSize(File folder){

        long result = 0l;

        if(folder == null) {

            File[] folderFiles = folder.listFiles();
            for (File file : folderFiles) {

                if (file.isDirectory()) {
                    result = result + calculatefolderSize(file);
                } else {
                    result = result + file.length();
                }

            }

        }

        return result;

    }

    /**
     * 텍스트 파일 생성.
     * @param filePath 생성 파일 경로
     * @param fileName 생성 파일 명
     * @param encoding 생성 할 인코딩
     * @param text 문자열
     * @param isOverWrite true : 파일이 존재할 경우 새 문자열로 내용 변경, false : 파일이 존재할 경우 새 문자열 내용 추가
     * @return true : 파일 생성 성공, false : 파일 생성 실패
     */
    public static boolean writeTextFile(String filePath, String fileName, String encoding, String text, boolean isOverWrite) throws IOException{

        if(filePath == null || filePath.length() <= 0 ||
                fileName == null || fileName.length() <= 0){
            return false;
        }

        File textFileFolder = new File(filePath);
        if(!textFileFolder.exists()){
            textFileFolder.mkdirs();
        }

        File textFile = new File(filePath, fileName);

        BufferedWriter bw = null;
        try {

            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(textFile, !isOverWrite), Charset.forName(encoding)));
            bw.write(text);
            bw.flush();

        } catch (Exception e){
            throw new IOException(e);
        } finally {

            try{

                bw.close();

            }catch (Exception e){
                // ignore
            }

        }

        return true;

    }

    /**
     * 텍스트 파일 읽어서 반환.
     * @param filePath 읽을 파일 경로
     * @param fileName 읽을 파일 명
     * @param encoding 읽어 드릴 인코딩
     * @return null : 경로 없음, 파일 없음, 오류 발생, 문자열 : 정상 반환
     */
    public static String readTextFile(String filePath, String fileName, String encoding){

        if(filePath == null || filePath.length() <= 0 ||
                fileName == null || fileName.length() <= 0){
            return null;
        }

        File textFile = new File(filePath, fileName);
        if(!textFile.exists()){
            return null;
        }

        StringBuilder result = new StringBuilder();
        BufferedReader br = null;
        try {

            br = new BufferedReader(new InputStreamReader(new FileInputStream(textFile), Charset.forName(encoding)));
            String readLine = "";
            while (((readLine = br.readLine()) != null)) {
                result.append(readLine + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            try {

                br.close();

            } catch (Exception e) {
                // ignore
            }

        }

        return result.toString();

    }

    /**
     * 참고 URL : https://www.sqlite.org/fileformat.html
     *
     * Sqlite Database Header Format
     * Offset	Size	Description
     * 0	16	The header string: "SQLite format 3\000"
     * 16	2	The database page size in bytes. Must be a power of two between 512 and 32768 inclusive, or the value 1 representing a page size of 65536.
     * 18	1	File format write version. 1 for legacy; 2 for WAL.
     * 19	1	File format read version. 1 for legacy; 2 for WAL.
     * 20	1	Bytes of unused "reserved" space at the end of each page. Usually 0.
     * 21	1	Maximum embedded payload fraction. Must be 64.
     * 22	1	Minimum embedded payload fraction. Must be 32.
     * 23	1	Leaf payload fraction. Must be 32.
     * 24	4	File change counter.
     * 28	4	Size of the database file in pages. The "in-header database size".
     * 32	4	Page number of the first freelist trunk page.
     * 36	4	Total number of freelist pages.
     * 40	4	The schema cookie.
     * 44	4	The schema format number. Supported schema formats are 1, 2, 3, and 4.
     * 48	4	Default page cache size.
     * 52	4	The page number of the largest root b-tree page when in auto-vacuum or incremental-vacuum modes, or zero otherwise.
     * 56	4	The database text encoding. A value of 1 means UTF-8. A value of 2 means UTF-16le. A value of 3 means UTF-16be.
     * 60	4	The "user version" as read and set by the user_version pragma.
     * 64	4	True (non-zero) for incremental-vacuum mode. False (zero) otherwise.
     * 68	4	The "Application ID" set by PRAGMA application_id.
     * 72	20	Reserved for expansion. Must be zero.
     * 92	4	The version-valid-for number.
     * 96	4	SQLITE_VERSION_NUMBER
     * @return
     */
    public static boolean isSqliteFileHeader(String filePath, String fileName) throws NullPointerException, IOException {

        if(filePath == null || filePath.length() <= 0 ||
                fileName == null || fileName.length() <= 0){
            throw new NullPointerException("Arguments is null.");
        }

        File file = new File(filePath, fileName);
        if(!file.exists()){
            throw new FileNotFoundException("Can not find file.");
        }

        boolean result = false;

        BufferedInputStream bis = null;
        try{

            bis = new BufferedInputStream(new FileInputStream(file));

            byte[] readByte = new byte[100];
            if (bis.read(readByte, 0, readByte.length) != -1){

                StringBuilder readResult = new StringBuilder();

                ByteBuffer byteBuffer = ByteBuffer.wrap(readByte);
                byteBuffer.order(ByteOrder.BIG_ENDIAN);

                byteBuffer.position(0);
                byte[] headerString = new byte[16];
                byteBuffer.get(headerString); // Always SQLite format 3 (hex : 53 51 4c 69 74 65 20 66 6f 72 6d 61 74 20 33 00)
                readResult.append("Header string : " + new String(headerString, Charset.forName("UTF-8")) + "\n");

                int pageSize = byteBuffer.getShort(16);
//                int calculatePageSize = (int)Math.pow(pageSize, 2);
                readResult.append("Page size : " + String.valueOf(pageSize) + "\n");

                byte writeVersion = byteBuffer.get(18); // 1 : legacy, 2 : WAL.
                readResult.append("Write version : " + String.valueOf((int)writeVersion) + "\n");

                byte readVersion = byteBuffer.get(19); // 1 : legacy, 2 : WAL.
                readResult.append("Read version : " + String.valueOf((int)readVersion) + "\n");

                byte reserved1 = byteBuffer.get(20); // usually 0
                readResult.append("Reserved space : " + String.valueOf((int)reserved1) + "\n");

                byte maximumPayload = byteBuffer.get(21); // 64
                readResult.append("Maximum embedded payload : " + String.valueOf((int)maximumPayload) + "\n");

                byte minimumPayload = byteBuffer.get(22); // 32
                readResult.append("Minimum embedded payload : " + String.valueOf((int)minimumPayload) + "\n");

                byte leafPayload = byteBuffer.get(23); // 32
                readResult.append("Leaf payload : " + String.valueOf((int)leafPayload) + "\n");

                int fileChangeCounter = byteBuffer.getInt(24);
                readResult.append("File change counter : " + String.valueOf(fileChangeCounter) + "\n");

                int inHeaderDatabaseSize = byteBuffer.getInt(28);
                readResult.append("In-header database size : " + String.valueOf(inHeaderDatabaseSize) + "\n");

                int freelistFirstPageNumber = byteBuffer.getInt(32);
                readResult.append("Freelist first page number : " + String.valueOf(freelistFirstPageNumber) + "\n");

                int freelistTotalNumber = byteBuffer.getInt(36);
                readResult.append("Freelist total page number : " + String.valueOf(freelistTotalNumber) + "\n");

                int schemaCookie = byteBuffer.getInt(40);
                readResult.append("Schema cookie : " + String.valueOf(schemaCookie) + "\n");

                int schemaFormatNumber = byteBuffer.getInt(44); // 1, 2, 3, 4
                readResult.append("Schema format number : " + String.valueOf(schemaFormatNumber) + "\n");

                int defaultPageCacheSize = byteBuffer.getInt(48);
                readResult.append("Default page cache size : " + String.valueOf(defaultPageCacheSize) + "\n");

                int bTreeLargestPageNumber = byteBuffer.getInt(52); // page number or 0
                readResult.append("Page number of the largest root b-tree page : " + String.valueOf(bTreeLargestPageNumber) + "\n");

                int encoding = byteBuffer.getInt(56); // 1 : UTF-8, 2 : UTF-16, 3 UTF-16be
                readResult.append("Encoding : " + String.valueOf(encoding) + "\n");

                int userVersion = byteBuffer.getInt(60);
                readResult.append("User version : " + String.valueOf(userVersion) + "\n");

                int incrementalVacuumMode = byteBuffer.getInt(64); // non 0 : mode, 0 : otherwise
                readResult.append("Incremental vacuum mode : " + String.valueOf(incrementalVacuumMode) + "\n");

                int applicationId = byteBuffer.getInt(68);
                readResult.append("Application id : " + String.valueOf(applicationId) + "\n");

                byteBuffer.position(72);
                byte[] reserved2 = new byte[20];
                byteBuffer.get(reserved2); // 0
                readResult.append("Reserved space for expansion : ");
                for (byte reserved2Value : reserved2) {
                    readResult.append(String.valueOf((int)reserved2Value) + " ");
                }
                readResult.append("\n");

                int versionValid = byteBuffer.getInt(92);
                readResult.append("Version valid : " + String.valueOf(versionValid) + "\n");

                int version = byteBuffer.getInt(96);
                readResult.append("Version : " + String.valueOf(version));

                Log.i("check", "cehck read result\n" + readResult.toString());

            }

            result = true;

        }catch (Exception e){
            throw new IOException(e);
        }finally {

            try {

                bis.close();

            }catch (Exception e){
                // ignore
            }

        }

        return result;

    }

}
