#!/bin/bash

echo "Converting BloodMate Documentation to PDF..."
echo

# Check if pandoc is installed
if ! command -v pandoc &> /dev/null; then
    echo "Pandoc is not installed. Please install pandoc first:"
    echo "https://pandoc.org/installing.html"
    echo
    echo "On macOS: brew install pandoc"
    echo "On Ubuntu: sudo apt-get install pandoc"
    echo
    echo "After installation, run this script again."
    exit 1
fi

# Convert Markdown to PDF using pandoc
echo "Converting documentation..."
pandoc BloodMate_Documentation.md -o BloodMate_Documentation.pdf \
    --pdf-engine=xelatex \
    --toc \
    --toc-depth=3 \
    --highlight-style=tango \
    --variable=geometry:margin=1in \
    --variable=fontsize:11pt \
    --variable=colorlinks:true \
    --variable=linkcolor:red \
    --variable=urlcolor:red

if [ $? -eq 0 ]; then
    echo
    echo "✅ Success! PDF documentation has been created:"
    echo "   📄 BloodMate_Documentation.pdf"
    echo
    # Try to open the PDF (varies by OS)
    if command -v xdg-open &> /dev/null; then
        xdg-open BloodMate_Documentation.pdf
    elif command -v open &> /dev/null; then
        open BloodMate_Documentation.pdf
    else
        echo "PDF created successfully. Please open BloodMate_Documentation.pdf manually."
    fi
else
    echo
    echo "❌ Error: Failed to convert documentation to PDF"
    echo "Please check that you have xelatex installed:"
    echo "https://www.latex-project.org/get/"
fi

echo