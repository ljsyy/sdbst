/**
 *
 */
function convert(str) {
    var en=["&amp;","&lt;","&gt;","&quot;","&apos;"];
    var ch=["&","<",">",'"',"'"];
    var ex=[/&amp;/g,/&lt;/g,/&gt;/g,/&quot;/g,/&apos;/g];
    for(var i=0;i<en.length;i++){
        if(str.indexOf(en[i])>=0){
            str=str.replace(ex[i],ch[i]);
        }
    }
    return str;
}
