package user50.sample.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Util_Image {

    /**
     * 비트맵 크기 및 유형 읽기.
     * 디코딩 시 inJustDecodeBounds 속성을 true로 설정하면 메모리 할당이 방지됩니다.
     * 그리고 비트 맵 객체에 null이 반환되지만 outWidth, outHeight, outMimeType은 설정됩니다.
     * 이 기법을 사용하면 비트맵을 생성(메모리 할당 포함)하기 전에 이미지 데이터의 크기와 유형을 읽을 수 있습니다.
     * 참고 URL : https://developer.android.com/topic/performance/graphics/load-bitmap#kotlin
     */
    fun getImageSizeWithNoMemoryAllocate(resources: Resources, resourceId: Int): Array<Int>{

        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeResource(resources, resourceId, options)
//        val imageHeight: Int = options.outHeight
//        val imageWidth: Int = options.outWidth
//        val imageType: String = options.outMimeType

        return arrayOf(options.outWidth, options.outHeight)

    }

    /**
     * 축소 버전을 메모리로 로드.
     * 이미지를 서브 샘플링하여 더 작은 버전을 메모리에 로드하도록 디코더에 지시하려면 BitmapFactory.Options 객체에서 inSampleSize를 true로 설정하면 됩니다.
     * 예를 들어, 해상도가 2048x1536이고 inSampleSize가 4로 디코딩된 이미지는 약 512x384의 비트맵을 생성합니다.
     * 이 비트맵을 메모리에 로드하면 전체 이미지에 12MB 대신 0.75MB가 사용됩니다(비트맵 구성은 ARGB_8888이라고 가정함).
     * 다음은 타겟 너비와 높이를 기준으로 2의 거듭제곱인 샘플 크기 값을 계산하는 메서드입니다.
     * 참고 URL : https://developer.android.com/topic/performance/graphics/load-bitmap#kotlin
     */
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {

        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize

    }

    /**
     * [calculateInSampleSize] 메서드를 사용하는 방법에 대한 예시.
     */
    fun decodeSampledBitmapFromResource(res: Resources, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {

        // First decode with inJustDecodeBounds=true to check dimensions
        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, resId, this)

            // Calculate inSampleSize
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            inJustDecodeBounds = false

            BitmapFactory.decodeResource(res, resId, this)
        }

    }

}