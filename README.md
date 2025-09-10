# Byte-by-Byte File Comparator

A fast and efficient C++ utility for comparing two files byte-by-byte to determine if they are identical. The program uses buffered reading for optimal performance on large files and provides timing information for comparison operations.

## Features

- **Byte-by-byte comparison**: Ensures 100% accuracy in file comparison
- **Buffered reading**: Configurable buffer size for optimal performance
- **Size optimization**: Quick file size check before byte comparison
- **Dual mode operation**: Command-line interface and interactive mode
- **Performance timing**: Shows comparison duration
- **Cross-platform**: Uses standard C++ libraries and filesystem API

## Requirements

- C++17 or later (for `std::filesystem`)
- Compatible with GCC, Clang, or MSVC

## Compilation

```bash
g++ -std=c++17 -O2 file_comparator.cpp -o file_comparator
```

Or with CMake:
```cmake
cmake_minimum_required(VERSION 3.8)
project(FileComparator)
set(CMAKE_CXX_STANDARD 17)
add_executable(file_comparator file_comparator.cpp)
```

## Usage

### Command Line Mode

```bash
# Basic usage
./file_comparator file1.txt file2.txt

# With custom buffer size (in bytes)
./file_comparator file1.txt file2.txt 8192
```

### Interactive Mode

Run the program without arguments to enter interactive mode:

```bash
./file_comparator
```

The program will prompt you for:
- Path to first file
- Path to second file  
- Buffer size (optional, defaults to 4096 bytes)

## Buffer Size Optimization

The default buffer size is 4096 bytes, but you can adjust it based on your needs:

- **Small files (< 1MB)**: 1024-4096 bytes
- **Medium files (1-100MB)**: 4096-16384 bytes  
- **Large files (> 100MB)**: 16384-65536 bytes
- **Very large files**: 65536+ bytes

## Examples

```bash
# Compare two documents
./file_comparator document1.pdf document2.pdf

# Compare with larger buffer for big files
./file_comparator video1.mp4 video2.mp4 32768

# Interactive mode example
./file_comparator
Enter path for first file: /path/to/file1.bin
Enter path for second file: /path/to/file2.bin  
Enter buffer size in bytes (default 4096): 8192
Files are identical.
Comparison took 0.045 seconds.
```

## Output

The program provides three types of output:

1. **Comparison result**: "Files are identical" or "Files are not identical"
2. **Timing information**: Duration of the comparison operation
3. **Error messages**: File access issues or invalid parameters

## Error Handling

- **File not found**: Program exits with error message
- **File access denied**: Program exits with error message  
- **Invalid buffer size**: Falls back to default 4096 bytes
- **Size mismatch**: Quick return without byte-by-byte comparison

## Performance Notes

- Files with different sizes are detected immediately without reading content
- Large files benefit from larger buffer sizes (reduced I/O calls)
- Binary mode reading ensures accurate comparison of all file types
- Memory usage scales with buffer size, not file size

## Algorithm

1. **Size Check**: Compare file sizes first for quick mismatch detection
2. **Buffered Reading**: Read both files in chunks of the specified buffer size
3. **Byte Comparison**: Use `std::equal` for efficient buffer comparison
4. **Early Exit**: Stop immediately when any difference is found

## License

This code is provided as-is for educational and practical use. Feel free to modify and distribute according to your needs.
