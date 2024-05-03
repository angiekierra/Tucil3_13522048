SOURCE_DIR=src
OUTPUT_DIR=bin

echo "Compiling Java files in $SOURCE_DIR..."
javac -d $OUTPUT_DIR $SOURCE_DIR/*.java

if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

echo "Running..."
cd $OUTPUT_DIR
java src/Main

exit 0
