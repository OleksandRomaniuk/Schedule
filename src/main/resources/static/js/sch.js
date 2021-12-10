

function setDefault() {
    console.log($("#inputSpec"));
    console.log($($("#inputSpec").val(${curSpec})));
    console.log(${curSpec});
    $("#inputSpec").val(${curSpec}).change();
}

setDefault();