function startKeypad(targetInputSelector, maxLength, isCreditCard) {
    var keyClearSelector = ".keypad-clear-key";
    var keySelector = ".keypad-key";

    // on "Clear" button press, clear target input
    $(keyClearSelector).click(function () {
        $(targetInputSelector).val("");
    });

    // on digit keys press, update target input
    $(keySelector).click(function () {
        var digit = ($(this).prop("value"));
        var oldVal = $(targetInputSelector).val();
        var oldValWithoutDashes = oldVal.replace(/-/g, ""); // for counting how many numbers have already been typed in
        if (oldVal.length < maxLength) {
            var newVal = oldVal;
            if (isCreditCard && oldValWithoutDashes.length != 0 && oldValWithoutDashes.length % 4 === 0) {
                // put dash after each 4 numbers
                newVal += "-";
            }
            newVal += digit;
            $(targetInputSelector).val(newVal);
        }
    });
}