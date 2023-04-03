package com.deemo.typing.Animation;



import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
    public static final int ANIMATION_LOOPING = 0;
    public static final int ANIMATION_NONLOOPING = 1;

    /**
     * TextureRegion（纹理区域） 表示 Texture（纹理）的一部分矩形区域（当然也可以表示整个 Texture 区域），
     * 可以用来绘制纹理中的一部分显示在屏幕上。
     * TextureRegion 可以通过记录 Texture 中的一个 坐标起点 和 从这个起点开始截取的宽高 来表示 Texture 的一部分。
     * TextureRegion 描述 Texture 区域时的 坐标点 是相对于 Texture 的左上角原点。
     * region = new TextureRegion(texture, 0, 0, 128, 128);
     */
    final Texture[] keyFrames;
    //帧持续时间
    final float frameDuration;

    public Animation (float frameDuration, Texture... keyFrames) {
        this.frameDuration = frameDuration;
        this.keyFrames = keyFrames;
    }

    /**
     *	获得当前应该是哪一帧
     */
    public Texture getKeyFrame (float stateTime, int mode) {
        int frameNumber = (int)(stateTime / frameDuration);

        //不循环最终保持最后一帧
        if (mode == ANIMATION_NONLOOPING) {
            frameNumber = Math.min(keyFrames.length - 1, frameNumber);
        } else {
            frameNumber = frameNumber % keyFrames.length;
        }
        //返回当前帧
        return keyFrames[frameNumber];
    }
}

