$.fn.enterKey = function (fnc) {
    return this.each(function () {
        $(this).keypress(function (ev) {
            var keycode = (ev.keyCode ? ev.keyCode : ev.which);
            if (keycode == '13') {
                fnc.call(this, ev);
            }
        })
    })
};

$(document).ready(function () {
    $('#search-input').enterKey(function () {
        search();
    })
});

function search() {
    window.location.href = 'results?q=' + $("#search-input").val();
}
