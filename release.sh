#!/bin/sh
if [ $# -eq 0 ]
  then
  	echo ""
    echo "Please provide following arguments:"
    echo "$0 [VERSION]"
    exit
fi

# parameter
VERSION=$1

echo clean up...
rm -r -f build

echo compiling...

gradle build
gradle jar
gradle javadoc

echo "copy files..."
OUTPUT="release/PostFX"
OUTPUTV="release/PostFX_$VERSION"
rm -r -f "$OUTPUT"
rm -r -f "$OUTPUTV"

mkdir -p "$OUTPUT/library"

# copy files
cp -f library.properties "release/PostFX.txt"
cp "build/libs/PostFX.jar" "$OUTPUT/library/"
cp -r shader "$OUTPUT/library/"
cp -r "build/docs/javadoc" "$OUTPUT/reference"

cp -r "examples" "$OUTPUT/"
cp library.properties "$OUTPUT/"
cp README.md "$OUTPUT/"
cp -r "src" "$OUTPUT/"

# create release files
cd "release/"
rm -f "PostFX.zip"
zip -r "PostFX.zip" "PostFX" -x "*.DS_Store"

# store it with version number
cd ..
mv -f "$OUTPUT" "$OUTPUTV"

echo "-------------------------"
echo "finished release $VERSION"