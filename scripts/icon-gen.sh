#!/bin/bash
# $> bash icon-gen.sh <version label> <project dir> <script sub-dir>
#
#
# process_icon version_num res_sub_dir current_work_dir target_dir
process_icon() {
    image_width=`/opt/ImageMagick/bin/identify -format %[fx:w] $3/app/src/main/res/mipmap-$2/ic_launcher.png` && let "image_width-=4"
    image_height=`/opt/ImageMagick/bin/identify -format %[fx:h] $3/app/src/main/res/mipmap-$2/ic_launcher.png` && let "image_height-=4"
    /opt/ImageMagick/bin/convert $3$4/marker.png -background '#0000' -fill white -gravity south -size 200x65 caption:$1 -composite -resize $image_widthx$image_height $3$4/intermediate.png
    /opt/ImageMagick/bin/convert -composite -gravity center $3/app/src/main/res/mipmap-$2/ic_launcher.png $3$4/intermediate.png $3$4/ic_launcher.png
    cp $3$4/ic_launcher.png $3/app/src/debug/res/mipmap-$2/
}
process_icon $1 xhdpi $2 $3
process_icon $1 xxhdpi $2 $3
process_icon $1 hdpi $2 $3
process_icon $1 mdpi $2 $3
rm $2$3/ic_launcher.png
rm $2$3/intermediate.png

