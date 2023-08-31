// Validate the selected file extension
function validateFileType(fileInput, allowedExtensions, errorMessage) {
    var filePath = fileInput.value;

    if (!allowedExtensions.exec(filePath)) {
        alert(errorMessage);
        fileInput.value = "";
        return false;
    }
    return true;
}

// Attach the validation function to the form submission
var form = document.querySelector('form');
form.onsubmit = function() {
    var htmlFileInput = document.querySelector('input[name="htmlFile"]');
    var cssFileInput = document.querySelector('input[name="cssFile"]');
    var jsFileInput = document.querySelector('input[name="jsFile"]');

    var allowedHtmlExtensions = /\.(html)$/i;
    var allowedCssExtensions = /\.(css)$/i;
    var allowedJsExtensions = /\.(js)$/i;

    var isValid = true;

    if (!validateFileType(htmlFileInput, allowedHtmlExtensions, "Please select a .html file.")) {
        isValid = false;
    }
    if (!validateFileType(cssFileInput, allowedCssExtensions, "Please select a .css file.")) {
        isValid = false;
    }
    if (!validateFileType(jsFileInput, allowedJsExtensions, "Please select a .js file.")) {
        isValid = false;
    }

    return isValid;
};
