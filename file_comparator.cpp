#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <chrono>
#include <filesystem>

bool compareFile(const std::string &file1, const std::string &file2, size_t bufferSize = 4096)
{
    // reading files
    std::ifstream f1(file1, std::ios::binary);
    std::ifstream f2(file2, std::ios::binary);

    if (!f1.is_open() || !f2.is_open())
    {
        std::cerr << "Error! could not open files.\n";
        std::exit(1);
    }

    // Comparing file sizes
    if (std::filesystem::file_size(file1) != std::filesystem::file_size(file2))
    {
        return false;
    }

    std::vector<char> buffer1(bufferSize);
    std::vector<char> buffer2(bufferSize);

    while (true)
    {
        f1.read(buffer1.data(), bufferSize);
        f2.read(buffer2.data(), bufferSize);

        std::streamsize bytesRead1 = f1.gcount();
        std::streamsize bytesRead2 = f2.gcount();

        if (bytesRead1 != bytesRead2)
            return false;

        if (bytesRead1 == 0 && bytesRead2 == 0)
            break;

        if (!std::equal(buffer1.begin(), buffer1.begin() + bytesRead1, buffer2.begin()))
            return false;
    }
    return true;
}

int main(int argc, char *argv[])
{
    std::string file1, file2;
    size_t bufferSize = 4096;

    if (argc >= 3)
    {
        // CLI mode
        file1 = argv[1];
        file2 = argv[2];
        if (argc >= 4)
        {
            try
            {
                bufferSize = std::stoul(argv[3]);
            }
            catch (...)
            {
                std::cerr << "Invalid buffer size. Using default 4096 bytes.\n";
            }
        }
    }
    else
    {
        // Interactive Mode
        std::cout << "Enter path for first file: ";
        std::getline(std::cin, file1);

        std::cout << "Enter path for second file: ";
        std::getline(std::cin, file2);

        std::cout << "Enter buffer size in bytes (default 4096): ";

        if (!(std::cin >> bufferSize) || bufferSize == 0)
        {
            std::cerr << "Invalid buffer size. Using default 4096 bytes.\n";
            bufferSize = 4096;
        }
    }

    // starting the time
    auto start = std::chrono::high_resolution_clock::now();

    bool result = compareFile(file1, file2, bufferSize);

    // stopping the time
    auto end = std::chrono::high_resolution_clock::now();

    // time elapsed
    std::chrono::duration<double> elapsed = end - start;

    if (result)
    {
        std::cout << "Files are identical.\n";
    }
    else
        std::cout << "Files are not identical.\n";

    // time taken for comparison
    std::cout << "Comparison took " << elapsed.count() << " seconds.\n";

    return 0;
}