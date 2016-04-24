/**
 * Created by johnny on 16/4/22.
 */

/**
 * 月份/时间自动补0
 * @param num
 * @param n
 */
function pad(num) {
    if(num < 10)
        return '0' + num;
    else
        return num;
}

function generateNowDateTime() {
    var d = new Date();
    var year = d.getFullYear();
    var month = pad(d.getMonth() + 1);
    var day = pad(d.getDate());
    var hour = pad(d.getHours());
    var min = pad(d.getMinutes());
    var sec = pad(d.getSeconds());
    return year + "-" + month + "-" + day + "T" + hour + ":" + min + ":" + sec;
}