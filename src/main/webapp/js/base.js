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


/**
 * 转换 标准日期到DateTimeLocal
 * @param date 字符串
 * @return String DateTimeLocal字符串
 */
function translateToDateTimeLocal(date) {
    var d = new Date(date);
    return d.getFullYear() + "-" + pad(d.getMonth() + 1) + "-"  + pad(d.getDate()) + "T" + pad(d.getHours()) + ":" + pad(d.getMinutes()) + ":" + pad(d.getSeconds());
}