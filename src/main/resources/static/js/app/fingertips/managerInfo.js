$(function () {
    // var matterId = [[${matterId}]]
    // var matterCode = [[${matterCode}]]
    // var dePhone = [[${dePhone}]]
    // console.log(matterId);
    // $("#matterId").text(matterId)
    // $("#matterCode").text(matterCode)
    // $("#dePhone").text(dePhone)

    getMaterialInfo($("#matterId").text());
    //绑定事件
    initEvent()
    //数据初始化
    initData()
// console.log("caseId:"+$("#caseId").text());
    // console.log(encodeURIComponent($("#dePhoneEnc").text()));

    // mui.showLoading("正在加载..","div");
})
//全局变量定义
var applicantType;
//个人办事对象
var basicApplicationVO = {
    agentCertificateNum: "",//代理人证件号码 ,
    agentCertificateType: "",// 代理人证件类型 ,
    agentName: "",// 代理人姓名 ,
    agentPhone: "",// 代理人手机 ,
    applicantName: "",//申报对象名称 ,
    applicantCertificateNum: "", //申报对象证件号码 ,
    applicantCertificateType: "",// 申报对象证件类型
    applicantPhone: "",//申请人手机
    memo: "",//备注
    matterId: "",// 事项 id ,
    postcode: "",// 申请人邮政编码
    addressVO: {
        city: "",//市区划编码
        cityName: "",//市名称
        detail: "",//详细地址
        district: "",//区区划编码
        districtName: "",//区名称
        province: "",//省区划编码
        provinceName: "",//省名称
    },//通信地址 ,
};
//企业办事对象
var corporationApplicationVO = {
    agentCertificateNum: "",//代理人证件号码 ,
    agentCertificateType: "",// 代理人证件类型 ,
    agentName: "",// 代理人姓名 ,
    agentPhone: "",// 代理人手机 ,
    applicantCertificateNum: "", //申报对象证件号码 ,
    applicantCertificateType: "",// 申报对象证件类型
    applicantCorpRep: "",//申报对象法定代表人
    applicantName: "",//申报对象名称 ,
    applicantType: "",// 申请人类型 ,
    contactName: "",// 联系人姓名 ,
    applicantPhone: "",//申请人手机
    // investProject: "",// 是否投资项目 ,
    // projectCode: "",// 项目代码 ,
    matterId: "",// 事项 id ,
    memo: "",//备注 ,
    postcode: "",// 申请人邮政编码
    // name: "",//办件名称 ,
    // projectId: "",//项目 id
    addressVO: {
        city: "",//市区划编码
        cityName: "",//市名称
        detail: "",//详细地址
        district: "",//区区划编码
        districtName: "",//区名称
        province: "",//省区划编码
        provinceName: "",//省名称
    },//通信地址 ,
};

//办件材料提交对象
var material = {
    caseId: $("#caseId").text(),
    openApiMaterialFileVOList: [],
    sceneId: "",
}
//寄件信息
var gotInfo;
var formMaterialId;
//创建基础信息
var data;
var caseId = $("#caseId").text();
var hallId;

//是否是取件方式页
var isLast = false;
//是否寄件人
var isSender = false;
//是否揽件地址
var isCollecter = false;

//用户资料记录
var user_obj = {
    agentCertificateNum:"",
    agentName:"",
    agentPhone:"",
    applicantName:"",
    applicantCertificateNum:"",
    applicantPhone:"",
    applicantType:"",
    enApplicantName:"",
    enApplicantCertificateNum:"",
    enApplicantPhone:""
}
//用户地址记录
var user_address = {
    provinceName:"",
    province:"",
    cityName:"",
    city:"",
    districtName:"",
    district:"",
    phone:"",
    agentPhone:""
}

//获取材料列表
function getMaterialInfo(matterId) {
    $.ajax({
        url: basePath() + "/app/fingertips/matter/material",
        // url:"http://19.202.141.212:8080/sdbst/app/fingertips/matter/material",
        data: {
            matterId: matterId
        },
        type: "get",
        dataType: "json",
        async:true,
        success: function (res) {
            var html = '';
            var data = res.data
            for (var i = 0; i < data.data.length; i++) {
                var openApiMaterialFileVO = {
                    //事项主键
                    materialId: data.data[i].id,
                    //材料获取方式
                    getType: 1,
                    //实收复印件数量
                    receiveCopyNum: data.data[i].pageCopyNum,
                    //实收原件数量
                    receiveOriginalNum: data.data[i].pageNum
                }
                //将该类材料放入材料集合中
                material.openApiMaterialFileVOList.push(openApiMaterialFileVO);
                if(data.data[i].getType != "" ) {//材料获取方式
                    // $("input:radio[value='1']").attr('checked','true');
                    $("#noEleFile").show();
                    $("#eleFile").hide();
                }else {
                    // $("input:radio[value='3']").attr('checked','true');
                    $("#eleFile").show();
                    $("#noEleFile").hide();
                    $("#hall_div").hide();

                }

                if (data.data[i].needElectronic == "1") {
                    html += '<li class="mui-table-view-cell mui-collapse" empty="00" type="'+data.data[i].type+'">'
                   // html += '<a style="color: #929292;" class="mui-navigate-right" href="#">' + (i + 1) + '、' + data.data[i].materialName + '</a>'
                    html += '<div style="">' + (i + 1) + '、' + data.data[i].materialName + '</div>'+'<a style="color: #929292;" class="mui-navigate-right" href="#"></a>'
                    html += '<div class="mui-collapse-content">'
                    html += ' <span class="gray_des">电子文件上传</span>'
                    html += '<div class="file"><div><label style="display: inline-block;height: 48px;"><img src="' + basePath() + '/img/fingertips/add_pic.jpg" alt="">'
                    html += '<input type="file" value="请选择图片" accept="image/*"></label></div>'
                    html += '</div>'
                    html += '<span class="gray_des">' + (data.data[i].sourcesDescribe ? "材料说明：" + data.data[i].sourcesDescribe : "") + '</span>'
                    html += '</div></li>'
                } else if (data.data[i].formId) {
                    //初始化电子表单材料ID
                    formMaterialId = data.data[i].id;
                    html += '<li class="mui-table-view-cell mui-collapse" fempty="00" type="'+data.data[i].type+'">'
                    html += '<a style="color: #929292;" class="mui-navigate-right" href="#">' + (i + 1) + '、' + data.data[i].materialName + '</a>'
                    html += '<div class="mui-collapse-content" style="text-align: center">'
                    html += '<button type="button" class="mui-btn mui-btn-primary"  onclick="getFormUrl(this,' + data.data[i].formId + ')">点击填写业务表单</button>'
                    html += '</div></li>'

                    $("body").append('<iframe id="form-frame-'+i+'" src="" style="display: none"></iframe>')
                } else {
                    html += '<li class="mui-table-view-cell mui-collapse">'
                    html += '<a style="color: #929292;" class="mui-navigate-right" href="#">' + (i + 1) + '、' + data.data[i].materialName + '</a>'
                    html += '<div class="mui-collapse-content">'
                    html += '<p class="gray_des">' + (data.data[i].pageNum ? "材料说明：递交原件数量       " + data.data[i].pageNum +"份": "") + '</p>'
                    html += '<p class="gray_des">' + (data.data[i].sourcesDescribe ? "材料说明：" + data.data[i].sourcesDescribe : "") + '</p>'
                    html += '</div></li>'
                    // html += '<li style="padding:11px 15px;color:#929292;">' + (i + 1) + '、' + data.data[i].materialName + '</li>'
                }
            }
            $(".material_list").html(html)
            if($(".material_list .mui-collapse").length>0){
                $(".material_list .mui-collapse").eq(0).addClass("mui-active");
            }

            uploadEvent()
        },
        error: function (err) {
            mui.toast("获取材料列表失败")
        }
    })
}

//获取表单url
function getFormUrl(obj,formId) {
    console.log(formId + "--" + material.caseId + "--" + formMaterialId + "--" + basicApplicationVO.applicantCertificateNum);
    var index = $(obj).parent().parent().index()
    mui.showLoading("正在加载表单","div");
    $.ajax({
        type: "post",
        url: basePath() + "/app/fingertips/case/getFormUrl",
        // url: "http://19.202.141.212:8080/sdbst/app/fingertips/case/getFormUrl",
        dataType: "json",
        async:true,
        data: {
            "formId": formId,
            "caseId": material.caseId,
            "materialId": formMaterialId,
            "urlType": "0",
            "dfrom": "2",
            "idCardNo": basicApplicationVO.applicantCertificateNum
        },
        success: function (result) {
            console.log(result.data);
            if (result.code == 200) {
                mui.hideLoading(function() {
                    var id = "form-frame-" + index
                    var $id = "#form-frame-" + index
                    //字符串处理
                    var url = result.data.replace("http://19.200.93.98:9200", "http://support.shunde.gov.cn:9203")
                    //设置src
                    $($id).attr("src", url);
                    //样式
                    $($id).attr("style", "width:100%;height:100%;");
                    //postMessage
                    document.getElementById(id).contentWindow.postMessage("", url);
                    //显示iframe
                    $($id).show();
                    $("form").hide()

                    window.addEventListener("message", function receive(e) {
                        console.log(e);
                        if (e.data) {
                            $(".material_list>li").eq(index).attr("fempty","01").children("div").children("button").removeAttr("onclick").css({
                                "background-color":"#ccc",
                                "color":"#fff",
                                "border":"1px solid #ccc"
                            }).text("该业务表单已填写完成")

                            setTimeout(function () {
                                $("form").show()
                                $($id).hide();
                            }, 2000)
                        }
                    }, false)
                })
            } else {
                mui.hideLoading(function(){
                    mui.toast("获取电子表单失败")
                });
            }
        },
        error:function(err){
            mui.hideLoading(function(){
                mui.toast("获取电子表单失败")
            });
        }
    });
}

function uploadEvent() {
    $(".file").on("change", "input[type='file']",function (e) {
        // var index = $(this).parent().parent().parent().parent().index()
        var $muiActive = $(".mui-active");
        var index = $muiActive.index()
        console.log(index)

        var filePath = $(this).val();
        var imgFile = e.target.files[0];
        if(imgFile) {
            var arr = filePath.split('\\');
            var fileName = arr[arr.length - 1];
            //显示名字
            // $(".showFileName1").html(fileName);
            var formData = new FormData();  // 创建form对象
            formData.append('file', imgFile);  // 通过append向form对象添加数据
            formData.append('other', 'other')  // 如果还需要传替他参数的话
            mui.showLoading("正在上传图片", "div");
            $.ajax({
                url: basePath() + "/app/fingertips/common/upload", //请求的接口地址
                // url: "http://19.202.141.212:8080/sdbst/app/fingertips/common/upload", //请求的接口地址
                type: 'POST',
                cache: false, //上传文件不需要缓存
                data: formData,
                dataType: "json",
                async: true,
                processData: false, // 不要去处理发送的数据
                contentType: false, // 不要去设置Content-Type请求头
                success: function (result) {
                    if(result.code == 200) {
                        //改变标志位
                        $(".mui-active").attr("empty","01");
                        //初始化上传电子材料对象
                        var openApiMaterialElectricFileVO = {
                            fileName: result.data.fileName,
                            fileSize: result.data.fileSize,
                            sourceType: "1",
                            storeUrl: result.data.storeUrl
                        }
                        //将对象放到材料集合中
                        if (material.openApiMaterialFileVOList[index].openApiMaterialElectricFileVOList) {
                            material.openApiMaterialFileVOList[index].openApiMaterialElectricFileVOList.push(openApiMaterialElectricFileVO);
                        } else {
                            material.openApiMaterialFileVOList[index].openApiMaterialElectricFileVOList = [];
                            material.openApiMaterialFileVOList[index].openApiMaterialElectricFileVOList.push(openApiMaterialElectricFileVO);
                        }
                        console.log(material);

                        //创建reader对象
                        var reader = new FileReader();
                        //读取完成后触发
                        reader.onload = function (ev) {
                            //获取图片的url
                            var _img_src = ev.target.result;
                            // console.log(_img_src)
                            //添加预览图片到容器框
                            var html = ''
                            html += '<div><img class="del" src="' + basePath() + '/img/fingertips/del.png" alt=""><img class="user_pic" src="' + _img_src + '" alt=""></div>'
                            $muiActive.find(".file").append(html)

                            mui.hideLoading(function () {
                                mui.toast("上传图片成功")
                            })

                            //绑定删除事件
                            $(".file .del").unbind("click").click(function () {
                                //li的相对位置
                                var idx = $(this).parent().parent().parent().parent().index()
                                //图片的相对位置
                                var my_idx = $(this).parent().index() - 1
                                material.openApiMaterialFileVOList[idx].openApiMaterialElectricFileVOList.splice(my_idx, 1)

                                if($(this).parent().parent().children().length == 2){
                                    //改变标志位
                                    $(".mui-active").attr("empty","00");
                                }
                                $(this).parent().remove()
                                mui.toast("删除图片成功")
                                console.log(material);
                            })
                        }
                        //获取到数据的url 图片将转成 base64 格式
                        reader.readAsDataURL(imgFile);
                    }else{
                        mui.hideLoading(function () {
                            mui.toast(result.msg)
                        })
                    }
                },
                error: function (err) {
                    mui.hideLoading(function () {
                        mui.toast("上传图片失败")
                        console.log(err);
                    })
                }
            })
        }
    });
}

//上一步
function showLast(now, prev) {
    $(now).hide()
    $(prev).show()
}

//第一项下一步
function oneNext() {
    //获取办事类型
    applicantType = $("input[name=applicant_type]:checked").val();
    //获取证件类型
    var agentCertificateType = $("#agentCertificateType").attr("code");
    //获取证件号码
    var agentCertificateNum = $("#agentCertificateNum").val().trim();
    //办事人姓名
    var agentName = $("#agentName").val().trim();
    //办事人手机号
    var agentPhone = $("#agentPhone").val().trim();

    //校验
    if(agentCertificateType == ""){
        mui.alert('请选择证件类型', '提示')
        return
    }else if(agentCertificateNum == ""){
        mui.alert('请输入证件号码', '提示')
        return
    }else if(agentName == ""){
        mui.alert('请输入办事人姓名', '提示')
        return
    }else if(agentPhone == ""){
        mui.alert('请输入办事人手机号', '提示')
        return
    }else if(agentPhone){
        var idExp = /^1(3|4|5|6|7|8|9)\d{9}$/
        if (!idExp.test(agentPhone)) {
            mui.alert('请输入正确的手机号', '提示')
            return
        }
    }

    if(agentCertificateType == "111") {
        var idExp = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|31)|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}([0-9]|x|X)$/
        if (!idExp.test(agentCertificateNum)) {
            mui.alert('请输入正确的身份证号码', '提示')
            return
        }
    }else if(agentCertificateType == "414"){
        var idExp = /^1[45][0-9]{7}|([P|p|S|s]\d{7})|([S|s|G|g]\d{8})|([Gg|Tt|Ss|Ll|Qq|Dd|Aa|Ff]\d{8})|([H|h|M|m]\d{8，10})$/
        if(!idExp.test(agentCertificateNum)){
            mui.alert('请输入正确的护照号码', '提示')
            return
        }
    }


    if (applicantType == "1") {
        //获取证件类型
        basicApplicationVO.agentCertificateType = agentCertificateType;
        //获取证件号码
        basicApplicationVO.agentCertificateNum = agentCertificateNum
        //办事人姓名
        basicApplicationVO.agentName = agentName;
        //办事人手机号
        basicApplicationVO.agentPhone = agentPhone;

        console.log(basicApplicationVO);

        mui.alert("信息保存成功","提示",function(){
            //隐藏
            $("#base-div").hide()
            $("#persion-div").show()
        })

        $("#applicantName_is_self").val($("#agentName").val().trim())
    } else if (applicantType == "2") {
        //获取证件类型
        corporationApplicationVO.agentCertificateType = agentCertificateType
        //获取证件号码
        corporationApplicationVO.agentCertificateNum = agentCertificateNum
        //办事人姓名
        corporationApplicationVO.agentName = agentName;
        //办事人手机号
        corporationApplicationVO.agentPhone = agentPhone;

        console.log(corporationApplicationVO);

        mui.alert("信息保存成功","提示",function(){
            //隐藏
            $("#base-div").hide()
            $("#enterprise-div").show()
        })
    }

    //保存代理人信息
    user_obj.agentCertificateNum = agentCertificateNum
    user_obj.agentName = agentName
    user_obj.agentPhone = agentPhone
    user_obj.applicantType = applicantType
}

//第二步，下一步,保存办件基本信息
function secondNext() {
    if (applicantType == "1") {
        //是否本人
        var is_self = $("input[name=is_self]:checked").val();
        //申请人姓名
        var applicantName = "";
        //证件类型
        var applicantCertificateType = "";
        //证件号码
        var applicantCertificateNum = "";
        if(is_self == "1"){
            //申请人姓名
            applicantName = $("#applicantName_is_self").val().trim();
            //证件类型
            applicantCertificateType = $("#agentCertificateType").attr("code");
            //证件号码
            applicantCertificateNum = $("#agentCertificateNum").val().trim();
        }else {
            //申请人姓名
            applicantName = $("#applicantName").val().trim();
            //证件类型
            applicantCertificateType = $("#agentCertificateType_applicant").attr("code");
            //证件号码
            applicantCertificateNum = $("#agentCertificateNum_applicant").val().trim();

            user_obj.applicantPhone = $("#user_contact_phone").text()
            user_obj.applicantName = applicantName
            user_obj.applicantCertificateNum = applicantCertificateNum
        }
        //申请人联系地址
        // var user_address = {
        //     city: "330100000000",
        //     cityName: "杭州市",
        //     detail: "云栖小镇",
        //     district: "330103000000",
        //     districtName: "东城区",
        //     province: "330000000000",
        //     provinceName: "浙江省"
        // }
        //备注
        var memo = $("#memo").val().trim();

        //校验
        if(applicantName == ""){
            mui.alert('请输入申请人姓名', '提示')
            return
        }else if(applicantCertificateType == ""){
            mui.alert('请选择证件类型', '提示')
            return
        }else if(applicantCertificateNum == ""){
            mui.alert('请输入证件号码', '提示')
            return
        }

        if(agentCertificateType == "111") {
            var idExp = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|31)|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}([0-9]|x|X)$/
            if (!idExp.test(agentCertificateNum)) {
                mui.alert('请输入正确的身份证号码', '提示')
                return
            }
        }else if(agentCertificateType == "414"){
            var idExp = /^1[45][0-9]{7}|([P|p|S|s]\d{7})|([S|s|G|g]\d{8})|([Gg|Tt|Ss|Ll|Qq|Dd|Aa|Ff]\d{8})|([H|h|M|m]\d{8，10})$/
            if(!idExp.test(agentCertificateNum)){
                mui.alert('请输入正确的护照号码', '提示')
                return
            }
        }

        basicApplicationVO.applicantName = applicantName
        //证件类型
        basicApplicationVO.applicantCertificateType = applicantCertificateType
        //证件号码
        basicApplicationVO.applicantCertificateNum = applicantCertificateNum
        //事项Id
        basicApplicationVO.matterId = $("#matterId").text()
        //申请人联系地址
        // basicApplicationVO.addressVO = user_address
        //备注
        basicApplicationVO.memo = memo
        console.log(basicApplicationVO);

    } else if (applicantType == "2") {
        //企业名称
        var applicantName = $("#enterpriseName").val().trim()
        //法定代表人
        var legal_representative = $("#legal_representative").val().trim()
        //证件类型
        var applicantCertificateType = $("#agentCertificateType_enterprise").attr("code")
        //证件号码
        var applicantCertificateNum = $("#applicantCertificateNum").val().trim()
        //申请人联系地址
        // var applicant_address = {
        //     city: "330100000000",
        //     cityName: "杭州市",
        //     detail: "云栖小镇",
        //     district: "330103000000",
        //     districtName: "东城区",
        //     province: "330000000000",
        //     provinceName: "浙江省"
        // }
        //备注
        var memo = $("#memo_enterprise").val().trim();

        //校验
        if(applicantName == ""){
            mui.alert('请输入企业名称', '提示')
            return
        }else if(legal_representative == ""){
            mui.alert('请输入法定代表人', '提示')
            return
        }else if(applicantCertificateType == ""){
            mui.alert('请选择证件类型', '提示')
            return
        }else if(applicantCertificateNum == ""){
            mui.alert('请输入证件号码', '提示')
            return
        }

        if(agentCertificateType == "001") {
            var idExp = /^[^_IOZSVa-z\W]{2}\d{6}[^_IOZSVa-z\W]{10}$/g
            if (!idExp.test(agentCertificateNum)) {
                mui.alert('请输入正确的统一社会信用代码证号码', '提示')
                return
            }
        }
        // else if(agentCertificateType == "003"){
        //     var idExp = /^1[45][0-9]{7}|([P|p|S|s]\d{7})|([S|s|G|g]\d{8})|([Gg|Tt|Ss|Ll|Qq|Dd|Aa|Ff]\d{8})|([H|h|M|m]\d{8，10})$/
        //     if(idExp.test(agentCertificateNum)){
        //         mui.alert('请输入正确的工商登记号（营业执照）号码', '提示')
        //         return
        //     }
        // }

        corporationApplicationVO.applicantName = applicantName
        corporationApplicationVO.applicantCorpRep = legal_representative
        corporationApplicationVO.applicantCertificateType = applicantCertificateType
        corporationApplicationVO.applicantCertificateNum = applicantCertificateNum
        corporationApplicationVO.matterId = $("#matterId").text()
        // corporationApplicationVO.addressVO = applicant_address
        corporationApplicationVO.memo = memo

        console.log(corporationApplicationVO);

        user_obj.enApplicantPhone = $("#user_contact_phone_enterprise").text()
        user_obj.enApplicantName = applicantName
        user_obj.enApplicantCertificateNum = applicantCertificateNum
    }

    //发起异步请求，提交办件基础信息
    if (caseId == ""){
        if (applicantType == "1") {
            if($(".click_add_address1").is(":visible")) {
                mui.alert('请填写申请人联系地址', '提示')
                return
            }
            data = {"applicantType": applicantType, "applicationType": 5, "basicApplicationVO": basicApplicationVO};
        } else {
            if($(".click_add_address2").is(":visible")) {
                mui.alert('请填写申请人联系地址', '提示')
                return
            }
            data = {
                "applicantType": applicantType,
                "applicationType": 5,
                "corporationApplicationVO": corporationApplicationVO
            };
        }
        mui.showLoading("正在提交信息","div");
        $.ajax({
            type: "post",
            url: basePath() + "/app/fingertips/case/add",
            // url: "http://19.202.141.212:8080/sdbst/app/fingertips/case/add",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data),//
            async:true,
            success: function (result) {
                if (result.code == 200) {
                    material.caseId = result.data;                //显示
                    caseId = result.data;
                    mui.hideLoading(function() {
                        mui.alert("提交信息成功","提示",function () {
                            if (applicantType == "1") {
                                $("#persion-div").hide()
                            } else {
                                $("#enterprise-div").hide()
                            }
                            $("#save-next-div").show()
                        })
                    })
                } else {
                    mui.hideLoading(function() {
                        mui.toast("提交信息失败，"+result.msg)
                    })
                }
            },
            error:function (err) {
                mui.hideLoading(function() {
                    mui.toast("提交信息失败，请仔细核对信息")
                })
            }
        });
    } else {
        if (applicantType == "1") {
            if($(".click_add_address1").is(":visible")) {
                mui.alert('请填写申请人联系地址', '提示')
                return
            }
            data = {"caseId":caseId,"applicantType": applicantType, "applicationType": 5, "basicApplicationVO": basicApplicationVO};
        } else {
            if($(".click_add_address2").is(":visible")) {
                mui.alert('请填写申请人联系地址', '提示')
                return
            }
            data = {
                "caseId":caseId,
                "applicantType": applicantType,
                "applicationType": 5,
                "corporationApplicationVO": corporationApplicationVO
            };
        }
        mui.showLoading("正在提交信息","div");
        $.ajax({
            type: "post",
            url: basePath() + "/app/fingertips/case/update",
            // url: "http://19.202.141.212:8080/sdbst/app/fingertips/case/update",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data),//
            async:true,
            success: function (result) {
                if (result.code == 200) {
                    // material.caseId = result.data;                //显示
                    // caseId = result.data;
                    mui.hideLoading(function() {
                        mui.alert("提交信息成功","提示",function () {
                            if (applicantType == "1") {
                                $("#persion-div").hide()
                            } else {
                                $("#enterprise-div").hide()
                            }
                            $("#save-next-div").show()
                        })
                    })
                } else {
                    mui.hideLoading(function() {
                        mui.toast("提交信息失败，"+result.msg)
                    })
                }
            },
            error:function (err) {
                mui.hideLoading(function() {
                    mui.toast("提交信息失败，请仔细核对信息")
                })
            }
        });
    }


    //存储用户资料信息
    console.log(user_obj);
    $.ajax({
        type: "post",
        url: basePath() + "/app/fingertips/updateUserHistory",
        dataType: "json",
        contentType: "application/json",
        async:true,
        data:JSON.stringify(user_obj),
        success:function (data) {

        },
        error:function (err) {
            console.log(err);
        }
    })

    //存储用户联系地址
    console.log(user_address);
    user_address.agentPhone = $("#dePhone").text()
    $.ajax({
        type: "post",
        url: basePath() + "/app/fingertips/updateUserAddress",
        dataType: "json",
        contentType: "application/json",
        async:true,
        data:JSON.stringify(user_address),
        success:function (data) {

        },
        error:function (err) {
            console.log(err);
        }
    })
}

//第三步，提交办件材料信息
function materialCreate() {
    //办件材料数据封装
    // material.openApiMaterialFileVOList[0].openApiMaterialElectricFileVOList=openApiMaterialElectricFileVOList;
    var hand_in_way = $("input[name='hand_in_way']:checked").val()
    console.log($("#hall").val())

    if (hand_in_way == undefined || hand_in_way == "") {
        mui.alert("请选择材料递交方式","提示")
        return
    }
    if ($("#hall").val() == "请选择办事大厅" && hand_in_way != "3") {
        mui.alert("请选择办事大厅","提示")
        return
    }
    console.log(material);
    for(var i=0;i<$(".material_list>li").length;i++){
        var $current = $(".material_list>li").eq(i)
        if($current.attr("empty") == "00" && $current.attr("type")== "1" ){
            mui.alert("材料递交第"+(i+1)+"项，请上传相关材料","提示")
            return
        }
        else if($current.attr("fempty") == "00"){
            mui.alert("材料递交第"+(i+1)+"项，资料没有上传完，请检查","提示")
            return
        }
        // else if($current.attr("fempty") == "00" && $current.attr("type")== "1" ){
        //     mui.alert("材料递交第"+(i+1)+"项，资料没有上传完，请检查","提示")
        //     return
        // }
    }

    mui.showLoading("正在创建材料信息","div");
    // mui.hideLoading(function(){
    //     mui.alert("创建材料信息成功","提示",function () {
    //         $("#save-next-div").hide();
    //         /*$("#save-commit-dive").show();*/
    //         if (hand_in_way == "1"){
    //             $("#post_message_div").show();
    //         } else {
    //             $("#save-commit-dive").show();
    //         }
    //     })
    // });
    $.ajax({
        url: basePath() + "/app/fingertips/case/material/add", /*接口域名地址*/
        // url: "http://19.202.141.212:8080/sdbst/app/fingertips/case/material/add", /*接口域名地址*/
        type: 'post',
        data: JSON.stringify(material),
        contentType: "application/json",
        dataType:"json",
        async:true,
        success: function (res) {
            console.log(res.data);
            if (res.code == 200) {
                mui.hideLoading(function(){
                    mui.alert("创建材料信息成功","提示",function () {
                        $("#save-next-div").hide();
                        /*$("#save-commit-dive").show();*/
                        if (hand_in_way == "1"){//快递送达
                            $("#post_message_div").show();
                        } else {//窗口提交
                            gotInfo= "";
                            $("#save-commit-dive").show();
                        }
                    })
                });
            } else {
                mui.hideLoading(function() {
                    mui.toast("创建材料信息失败，"+res.msg)
                })
            }
        },
        error:function(err){
            mui.hideLoading(function() {
                mui.toast("创建材料信息失败")
                console.log(err);
            })
        }
    })
}


//提交办件信息
function submitHandling() {
    //取件方式
    var get_way = $("input[name=get_way]:checked").val()
    var outletRecipientName = ""
    var outletRecipientPhone = ""
    var outletRecipientPostcode= ""

    var city = "";
    var cityName = "";
    var detail = "";
    var district = "";
    var districtName = "";
    var province = "";
    var provinceName = "";

    var obj = {};
    if (get_way == "2") {
        outletRecipientName = $("#send_username").text().trim()
        outletRecipientPhone = $("#send_phone").text().trim()
        outletRecipientPostcode = $("#save-commit-dive").attr("postcode")
        var $save = $("#save-commit-dive")
        city = $save.attr("city")
        cityName = $save.attr("cityname");
        detail = $save.attr("detail");;
        district = $save.attr("district");
        districtName = $save.attr("districtname");
        province = $save.attr("province");;
        provinceName = $save.attr("provincename");

        if($(".click_add_address3").is(":visible")) {
            mui.alert('请填写配送地址', '提示')
            return
        }

        obj = {
            applicationInfoVO: {
                ...data
            },
            caseId: caseId,
            serviceInfoVO: {
                outletType: get_way,
                outletRecipientName: outletRecipientName,
                outletRecipientPhone: outletRecipientPhone,
                outletRecipientAddress: {
                    city: city,
                    cityName: cityName,
                    detail: detail,
                    district: district,
                    districtName: districtName,
                    province: province,
                    provinceName: provinceName
                },
                outletRecipientPostcode: outletRecipientPostcode,
                hallId: hallId
            },
            gotInfo:gotInfo
        }
    } else {
        outletRecipientName = $("#send_get_username").val().trim()
        outletRecipientPhone = $("#send_get_phone").val().trim()
        if(!outletRecipientName) {
            mui.alert('请填写取件人名称', '提示')
            return
        }else if(!outletRecipientPhone){
            mui.alert('请填写取件人手机', '提示')
            return
        }else if(outletRecipientPhone){
            var idExp = /^1(3|4|5|6|7|8|9)\d{9}$/
            if (!idExp.test(outletRecipientPhone)) {
                mui.alert('请输入正确的手机号', '提示')
                return
            }
        }

        obj = {
            applicationInfoVO: {
                ...data
            },
            caseId: caseId,
            serviceInfoVO: {
                outletType: get_way,
                outletRecipientName: outletRecipientName,
                outletRecipientPhone: outletRecipientPhone,
                hallId: hallId
            },
            gotInfo:gotInfo
        }
    }

    // console.log(obj)
    // console.log(JSON.stringify(obj))
    mui.showLoading("正在提交信息","div");
    $.ajax({
        url: basePath() + "/app/fingertips/case/submit", /*接口域名地址*/
        // url: "http://localhost:8080/sdbst/app/fingertips/case/submit", /*接口域名地址*/
        type: 'post',
        data: JSON.stringify(obj),
        contentType: "application/json",
        dataType:"json",
        async:true,
        success: function (res) {
            if (res.code == 200) {
                mui.hideLoading(function(){
                    mui.alert("申请信息提交成功，请留意结果","提示",function(){
                        // window.history.go(-1)
                        window.location.href = basePath()+"/app/fingertips/getFingertipsHistory?link="+$("#link").text()
                    })
                });
            } else {
                mui.hideLoading(function() {
                    mui.toast("提交失败，"+res.msg)
                })
            }
        },
        error:function (err) {
            mui.hideLoading(function() {
                mui.toast("提交失败")
                console.log(err);
            })
        }
    })
}

//提交寄件信息
function postCreate() {
    if($(".click_add_address_post").is(":visible")) {
        mui.alert('请填写寄件人地址', '提示')
        return
    }
    if($(".click_add_address_collect").is(":visible")) {
        mui.alert('请填写揽件地址', '提示')
        return
    }

    var $Post = $("#post_message_div")
    var sender_postcode = $Post.attr("sender_postcode")
    var sender_provinceName = $Post.attr("sender_provinceName")
    var sender_province = $Post.attr("sender_province")
    var sender_cityName = $Post.attr("sender_cityName")
    var sender_city = $Post.attr("sender_city")
    var sender_districtName = $Post.attr("sender_districtName")
    var sender_district = $Post.attr("sender_district")
    var sender_detail = $Post.attr("sender_detail")

    var collect_postcode = $Post.attr("collect_postcode")
    var collect_provinceName = $Post.attr("collect_provinceName")
    var collect_province = $Post.attr("collect_province")
    var collect_cityName = $Post.attr("collect_cityName")
    var collect_city = $Post.attr("collect_city")
    var collect_districtName = $Post.attr("collect_districtName")
    var collect_district = $Post.attr("collect_district")
    var collect_detail = $Post.attr("collect_detail")

    sender_name = $("#post_message_username").text().trim()
    sender_phone = $("#post_message_phone").text().trim()

    collect_name = $("#collect_message_username").text().trim()
    collect_phone = $("#collect_message_phone").text().trim()

    gotInfo = {
        collect: {
            name:collect_name,
            postCode:collect_postcode,
            mobile:collect_phone,
            prov: collect_provinceName,
            city: collect_cityName,
            county: collect_districtName,
            address: collect_detail,
        },
        sender: {
            name:sender_name,
            postCode:sender_postcode,
            mobile:sender_phone,
            prov: sender_provinceName,
            city: sender_cityName,
            county: sender_districtName,
            address: sender_detail,
        },
        receiver: {
            name:receiver_name,
            postCode:receiver_postCode,
            phone:receiver_phone,
            prov: receiver_prov,
            city: receiver_city,
            county: receiver_county,
            address: receiver_address,
        },
        remark:$("#post_desc").val().trim(),
        dePhoneEnc:$("#dePhoneEnc").text().trim(),
        matterId:$("#matterId").text(),
        agentCertificateNum : $("#agentCertificateNum").val().trim(),
        agentName : $("#agentName").val().trim(),
        agentPhone : $("#agentPhone").val().trim(),
        caseId:caseId
    }
    mui.alert("寄件信息提交成功","温馨提示",function () {
        $("#post_message_div").hide();
        $("#save-commit-dive").show();
    })
console.log(gotInfo);
    // mui.showLoading("正在提交信息","div");
    // $.ajax({
    //     // url: basePath() + "/app/fingertips/ems/createOrder", /*接口域名地址*/
    //     url: "http://19.202.141.200:8080/sdbst/app/fingertips/ems/createOrder", /*接口域名地址*/
    //     type: 'post',
    //     data: JSON.stringify(gotInfo),
    //     contentType: "application/json",
    //     dataType:"json",
    //     async:true,
    //     success: function (res) {
    //         if (res.code == 200) {
    //             mui.hideLoading(function(){
    //                 // mui.alert("申请信息提交成功，请留意结果","提示",function(){
    //                 //     // window.history.go(-1)
    //                 //     window.location.href = basePath()+"/app/fingertips/getFingertipsHistory?authKey="+$("#dePhoneEnc").text()
    //                 // })
    //                 mui.alert("寄件信息提交成功","温馨提示",function () {
    //                     $("#post_message_div").hide();
    //                     $("#save-commit-dive").show();
    //                 })
    //             });
    //         } else {
    //             mui.hideLoading(function() {
    //                 mui.toast("提交失败，"+res.msg)
    //             })
    //         }
    //     },
    //     error:function (err) {
    //         mui.hideLoading(function() {
    //             mui.toast("提交失败")
    //             console.log(err);
    //         })
    //     }
    // })
}

function sendPost() {
    mui.showLoading("正在提交信息","div");
    $.ajax({
        url: basePath() + "/app/fingertips/ems/createOrder", /*接口域名地址*/
        // url: "http://19.202.141.212:8080/sdbst/app/fingertips/ems/createOrder", /*接口域名地址*/
        type: 'post',
        data: JSON.stringify(gotInfo),
        contentType: "application/json",
        dataType:"json",
        async:true,
        success: function (res) {
            if (res.code == 200) {
                mui.hideLoading(function(){
                    // mui.alert("申请信息提交成功，请留意结果","提示",function(){
                    //     // window.history.go(-1)
                    //     window.location.href = basePath()+"/app/fingertips/getFingertipsHistory?authKey="+$("#dePhoneEnc").text()
                    // })
                    mui.alert("寄件信息提交成功","温馨提示",function () {
                        $("#post_message_div").hide();
                        $("#save-commit-dive").show();
                    })
                });
            } else {
                mui.hideLoading(function() {
                    mui.toast("提交失败，"+res.msg)
                })
            }
        },
        error:function (err) {
            mui.hideLoading(function() {
                mui.toast("提交失败")
                console.log(err);
            })
        }
    })
}
//办事指引
// function bsLeader() {
//     /* console.log("办事指引")
//      document.getElementById("bs-leader").style.display="";*/
// }
//
// //将数据保存到服务端
// function saveData(name, data) {
//     var dataStr = {name: data};
//     $.ajax({
//         url: basePath() + "/app/fingertips/case/material/add",
//         type: 'post',
//         data: JSON.stringify(dataStr),
//         contentType: "application/json",
//         success: function (result) {
//             console.log(result.data);
//             if (result.code == 200) {
//                 console.log("数据保存成功！")
//                 //
//             } else {
//                 console.log("button");
//             }
//         }
//     })
// }

//初始化点击事件
function initEvent() {
    //个人办事-联系人地址列表跳转
    $("#user_address").click(function () {
        $("#persion-div").hide()
        $("#address_operate").show()
        isLast = false;
        isSender = false;
        isCollecter = false;
    })
    //企业办事-联系人地址列表跳转
    $("#user_address_enterprise").click(function () {
        $("#enterprise-div").hide()
        $("#address_operate").show()
        isLast = false;
        isSender = false;
        isCollecter = false;
    })
    //取件方式配送地址跳转
    $("#send_div").click(function () {
        $("#save-commit-dive").hide()
        $("#address_operate").show()
        isLast = true
        isSender = false;
        isCollecter = false;
    })
    //寄件人地址-联系人地址列表跳转
    $("#post_user_address").click(function () {
        $("#post_message_div").hide()
        $("#address_operate").show()
        isLast = false;
        isSender = true;
        isCollecter = false;
    })
    //揽件地址-联系人地址列表跳转
    $("#collect_user_address").click(function () {
        $("#post_message_div").hide()
        $("#address_operate").show()
        isLast = false;
        isSender = false;
        isCollecter = true;
    })

    //新增地址
    $(".add_btn").click(function () {
        $("#location").val("")
        $("#address_list").hide()
        $("#address_operate").show()
        $(".footer_btns>.del_btn").hide()
    })
    $(".save_btn").click(function () {
        $("#address_operate").hide()
        $("#address_list").show()
    })
    //编辑地址
    $(".address_edit").click(function (e) {
        e.stopPropagation()
        $("#address_list").hide()
        $("#address_operate").show()
        $(".footer_btns>.del_btn").show()
        //显示当前数据
        var currentLi = $(this).parent()
        $("#username_operate").val(currentLi.find(".user_name").text())
        $("#phone_operate").val(currentLi.find(".user_phone").text())
        showLocation(currentLi.find(".user_location"))
        $("#detail_operate").val(currentLi.find(".user_address").text())
        $("#postcode_operate").val("")
    })
    $(".update_btn").click(function () {
        var username_operate = $("#username_operate").val().trim()
        var phone_operate = $("#phone_operate").val().trim()
        var location = $("#location").val().trim()
        var detail_operate = $("#detail_operate").val().trim()
        var postcode_operate = $("#postcode_operate").val().trim()

        var locationArr = location.split(" ")
        var provinceName = locationArr[0]
        var cityName = locationArr[1]
        var districtName = locationArr[2]

        if(!username_operate){
            mui.alert("请填写联系人姓名")
            return
        }else if(!phone_operate){
            mui.alert("请填写手机号码")
            return
        }else if(!location){
            mui.alert("请选择所在地区")
            return
        }else if(!detail_operate){
            mui.alert("请填写详细地址")
            return
        }else if(!postcode_operate){
            mui.alert("请填写邮政编码")
            return
        }
        if(phone_operate){
            var idExp = /^1(3|4|5|6|7|8|9)\d{9}$/
            if (!idExp.test(phone_operate)) {
                mui.alert('请输入正确的手机号', '提示')
                return
            }
        }
        if(postcode_operate){
            var idExp = /^[1-9][0-9]{5}$/
            if (!idExp.test(postcode_operate)) {
                mui.alert('请输入正确的邮政编码', '提示')
                return
            }
        }

        $("#address_operate").hide()
        if (isLast) {
            var code = getCode2(provinceName, cityName, districtName)
            $("#save-commit-dive").show().attr({
                postcode:postcode_operate,
                provinceName:provinceName,
                province:code.province,
                cityName:cityName,
                city:code.city,
                districtName:districtName,
                district:code.district,
                detail:detail_operate
            })
            $("#send_div").attr({"postcode":postcode_operate})
            $("#send_username").css("display", "inline-block").text(username_operate);
            $("#send_phone").css("display", "inline").text(phone_operate);
            $("#send_address").css("display", "block").text(location.split(" ").join("") + detail_operate);
            $(".click_add_address3").hide()
        } else if (isCollecter) {
            var code = getCode2(provinceName, cityName, districtName)
            $("#post_message_div").show().attr({
                collect_postcode:postcode_operate,
                collect_provinceName:provinceName,
                collect_province:code.province,
                collect_cityName:cityName,
                collect_city:code.city,
                collect_districtName:districtName,
                collect_district:code.district,
                collect_detail:detail_operate
            })
            $("#collect_div").attr({"postcode":postcode_operate})
            $("#collect_message_username").css("display", "inline-block").text(username_operate);
            $("#collect_message_phone").css("display", "inline").text(phone_operate);
            $("#collect_message_addr").css("display", "block").text(location.split(" ").join("") + detail_operate);
            $(".click_add_address_collect").hide()
        } else if (isSender) {
            var code = getCode2(provinceName, cityName, districtName)
            $("#post_message_div").show().attr({
                sender_postcode:postcode_operate,
                sender_provinceName:provinceName,
                sender_province:code.province,
                sender_cityName:cityName,
                sender_city:code.city,
                sender_districtName:districtName,
                sender_district:code.district,
                sender_detail:detail_operate
            })
            $("#post_div").attr({"postcode":postcode_operate})
            $("#post_message_username").css("display", "inline-block").text(username_operate);
            $("#post_message_phone").css("display", "inline").text(phone_operate);
            $("#post_message_addr").css("display", "block").text(location.split(" ").join("") + detail_operate);
            $(".click_add_address_post").hide()
        } else {
            if (applicantType == "1") {
                $("#persion-div").show().attr("postcode", postcode_operate)
                $("#user_contact_username").css("display", "inline-block").text(username_operate);
                $("#user_contact_phone").css("display", "inline").text(phone_operate);
                $("#user_contact_address").css("display", "block").text(location.split(" ").join("") + detail_operate);
                $(".click_add_address1").hide()
                var code = getCode2(provinceName, cityName, districtName)

                //申请人手机
                basicApplicationVO.applicantPhone = phone_operate

                basicApplicationVO.addressVO.provinceName = provinceName
                basicApplicationVO.addressVO.province = code.province
                basicApplicationVO.addressVO.cityName = cityName
                basicApplicationVO.addressVO.city = code.city
                basicApplicationVO.addressVO.districtName = districtName
                basicApplicationVO.addressVO.district = code.district
                basicApplicationVO.addressVO.detail = detail_operate
                basicApplicationVO.postcode = postcode_operate


                user_address.phone = phone_operate
                user_address.provinceName = provinceName
                user_address.province = code.province
                user_address.cityName = cityName
                user_address.city = code.city
                user_address.districtName = districtName
                user_address.district = code.district
                user_address.detail = detail_operate
                user_address.postcode = postcode_operate

                console.log(basicApplicationVO);
            } else {
                $("#enterprise-div").show().attr("postcode", postcode_operate)
                $("#user_contact_username_enterprise").css("display", "inline-block").text(username_operate);
                $("#user_contact_phone_enterprise").css("display", "inline").text(phone_operate);
                $("#user_contact_address_enterprise").css("display", "block").text(location.split(" ").join("") + detail_operate);
                $(".click_add_address2").hide()

                var code = getCode2(provinceName, cityName, districtName)

                //申请人手机
                corporationApplicationVO.applicantPhone = phone_operate

                corporationApplicationVO.addressVO.provinceName = provinceName
                corporationApplicationVO.addressVO.province = code.province
                corporationApplicationVO.addressVO.cityName = cityName
                corporationApplicationVO.addressVO.city = code.city
                corporationApplicationVO.addressVO.districtName = districtName
                corporationApplicationVO.addressVO.district = code.district
                corporationApplicationVO.addressVO.detail = detail_operate
                corporationApplicationVO.postcode = postcode_operate

                user_address.phone = phone_operate
                user_address.provinceName = provinceName
                user_address.province = code.province
                user_address.cityName = cityName
                user_address.city = code.city
                user_address.districtName = districtName
                user_address.district = code.district
                user_address.detail = detail_operate
                user_address.postcode = postcode_operate

                console.log(corporationApplicationVO);
            }
        }
    })

    //材料详情
    $("#toDetail").click(function () {
        window.location.href = basePath()+"/app/fingertips/shenBao?matterId=" + $("#matterId").text()
    })

    $(".del_btn").click(function () {
        $("#address_operate").hide()
        $("#address_list").show()
    })
    //选择地址
    $(".address_list>li").click(function () {
        $("#address_list").hide()
        $(".click_add_address").hide()
        if (isLast) {
            $("#save-commit-dive").show()
            $("#send_username").css("display", "inline-block").text($(this).find(".user_name").text());
            $("#send_phone").css("display", "inline").text($(this).find(".user_phone").text());
            $("#send_address").css("display", "block").text($(this).find(".user_address").text());
        } else {
            if (applicantType == "1") {
                $("#persion-div").show()
                $("#user_contact_username").css("display", "inline-block").text($(this).find(".user_name").text());
                $("#user_contact_phone").css("display", "inline").text($(this).find(".user_phone").text());
                $("#user_contact_address").css("display", "block").text($(this).find(".user_address").text());

                // basicApplicationVO.addressVO.provinceName =
            } else {
                $("#enterprise-div").show()
                $("#user_contact_username_enterprise").css("display", "inline-block").text($(this).find(".user_name").text());
                $("#user_contact_phone_enterprise").css("display", "inline").text($(this).find(".user_phone").text());
                $("#user_contact_address_enterprise").css("display", "block").text($(this).find(".user_address").text());
            }
        }
    })

    //办事指引
    $("#save-commit-dive>.coding_direct>.guide").click(function (e) {
        e.stopPropagation()
        $(".black_shade").show()
        $("#work_guide").show()
    })
    $("#post_message_div>.coding_direct>.guide").click(function (e) {
        e.stopPropagation()
        $(".black_shade").show()
        $("#work_guide").show()
    })
    $("#work_guide>p").click(function (e) {
        e.stopPropagation()
        $(".black_shade").hide()
        $("#work_guide").hide()
    })

    //证件类型点击
    $(".agentCertificateTypeDiv").click(function (e) {
        e.stopPropagation()
        if ($("#agentCertificateTypeUl").is(":hidden")) {
            $("#agentCertificateTypeUl").show()
        } else {
            $("#agentCertificateTypeUl").hide()
        }
    })
    $(".agentCertificateTypeDiv_applicant").click(function (e) {
        e.stopPropagation()
        if ($("#agentCertificateTypeUl_applicant").is(":hidden")) {
            $("#agentCertificateTypeUl_applicant").show()
        } else {
            $("#agentCertificateTypeUl_applicant").hide()
        }
    })
    $(".agentCertificateTypeDiv_enterprise").click(function (e) {
        e.stopPropagation()
        if ($("#agentCertificateTypeUl_enterprise").is(":hidden")) {
            $("#agentCertificateTypeUl_enterprise").show()
        } else {
            $("#agentCertificateTypeUl_enterprise").hide()
        }
    })
    //办事大厅点击选择
    $(".hallDiv").click(function (e) {
        e.stopPropagation()
        if ($("#hallUI").is(":hidden")) {
            $("#hallUI").show()
        } else {
            $("#hallUI").hide()
        }
    })
    // $("#location").click(function(){
    //     $(".picker-items-col").find(".picker-selected").removeClass("picker-selected")
    //     $(".picker-items-col").eq(0).find("[data-picker-value="+provinceName+"]").click()
    //     $(".picker-items-col").eq(1).find("[data-picker-value="+cityName+"]").click()
    //     $(".picker-items-col").eq(2).find("[data-picker-value="+districtName+"]").click()
    // })

    //是否本人选择事件
    $("input[name=is_self]").click(function () {
        var currentVal = $("input[name=is_self]:checked").val();
        if(currentVal == "2"){
            $("#applicantType").show();
            $("#applicantNum").show();
            $("#applicantName_is_self").parent().hide()
            $("#applicantName").parent().show()
        }else{
            $("#applicantType").hide();
            $("#applicantNum").hide();
            $("#applicantName_is_self").parent().show()
            $("#applicantName").parent().hide()
            $("#applicantName_is_self").val($("#agentName").val().trim())
        }
    })

    //材料递交方式点击
    $("input[name=hand_in_way]").click(function () {
        var hand_in_way = parseInt($("input[name=hand_in_way]:checked").val())
        if (hand_in_way == '3'){
            hand_in_way = 0;
        }
        for (var i = 0; i < material.openApiMaterialFileVOList.length; i++) {
            material.openApiMaterialFileVOList[i].getType = hand_in_way
        }
        if (hand_in_way == 2) {
            $("#hand_in_div").show()
        } else {
            $("#hand_in_div").hide()
        }
    })

    //取件方式点击事件
    $("input[name=get_way]").click(function () {
        var get_way = $("input[name=get_way]:checked").val()
        if (get_way == "2") {
            $("#send_div").show()
            $("#send_get_div").hide()
        } else {
            $("#send_div").hide()
            $("#send_get_div").show()
        }
    })

    $(document).click(function () {
        $("#agentCertificateTypeUl").hide()
        $("#agentCertificateTypeUl_applicant").hide()
        $("#agentCertificateTypeUl_enterprise").hide()
    })
}

function getCode2(provinceName,cityName,districtName){
    var obj = {
        province:regions["provinces"][provinceName].id,
        city:regions["provinces"][provinceName]["cities"][cityName].id,
        district:regions["provinces"][provinceName]["cities"][cityName]["areas"][districtName].id
    }

    return obj
}

var provinceName = ""
var cityName = ""
var districtName = ""

function showLocation(obj) {
    var province = obj.attr("province")

    var city = obj.attr("city")

    var district = obj.attr("district")


    var provincesKeyArr = Object.keys(regions["provinces"])
    var provincesValArr = Object.values(regions["provinces"])
    for (var i = 0; i < provincesKeyArr.length; i++) {
        if (provincesValArr[i].id == province) {
            provinceName = provincesKeyArr[i]
            break
        }
    }

    var citiesKeyArr = Object.keys(regions["provinces"][provinceName]["cities"])
    var citiesValArr = Object.values(regions["provinces"][provinceName]["cities"])
    for (var i = 0; i < citiesKeyArr.length; i++) {
        if (citiesValArr[i].id == city) {
            cityName = citiesKeyArr[i]
            break
        }
    }

    var districtKeyArr = Object.keys(regions["provinces"][provinceName]["cities"][cityName]["areas"])
    var districtValArr = Object.values(regions["provinces"][provinceName]["cities"][cityName]["areas"])
    for (var i = 0; i < districtKeyArr.length; i++) {
        if (districtValArr[i].id == district) {
            districtName = districtKeyArr[i]
            break
        }
    }

    console.log(provinceName);
    console.log(cityName);
    console.log(districtName);

    $("#location").val(provinceName + " " + cityName + " " + districtName)

    // $(".picker-items-col").find(".picker-selected").removeClass("picker-selected").
    // $(".picker-items-col").eq(1).find("[data-picker-value="+provinceName+"]").addClass("picker-selected")
    // $(".picker-items-col").eq(2).find("[data-picker-value="+cityName+"]").addClass("picker-selected")
    // $(".picker-items-col").eq(3).find("[data-picker-value="+districtName+"]").addClass("picker-selected")

}
var receiver_name= "";
var receiver_postCode= "";
var receiver_phone= "";
var receiver_prov= "";
var receiver_city= "";
var receiver_county= "";
var receiver_address= "";

var hallAddress = new Array();

function getHallInfo() {
    $.ajax({
        url: basePath() + "/app/fingertips/common/hallInfoList",
        // url:"http://19.202.141.200:8080/sdbst/app/fingertips/common/hallInfoList",
        data: {
            matterId: $("#matterId").text()
        },
        type: "get",
        dataType: "json",
        async:true,
        success: function (data) {
            console.log(data);//多个地址选择
            // hallId = data.data[0].id;

            if (data.data.length>=1){
                var html = '';
                for (var i = 0; i < data.data.length; i++) {
                    //现在只显示“区行政服务中心东座二楼”的地址，后面需要显示全部的话，删掉if这块
                    if ((data.data[i].name.search("区行政服务中心东座二楼"))!= -1 ){
                        html += '<li code="' + data.data[i].id + '">' + data.data[i].name + '</li>'
                        var html1 = {};
                        html1.name = data.data[i].name;
                        html1.postCode = data.data[i].zipCode;
                        html1.phone = data.data[i].telephone;
                        html1.prov = data.data[i].provinceName;
                        html1.city = data.data[i].cityName;
                        html1.county = data.data[i].districtName;
                        html1.address = data.data[i].address;
                        hallAddress[i] = html1;
                    }
                    // html += '<li code="' + data.data[i].id + '">' + data.data[i].name + '</li>'
                    // var html1 = {};
                    // html1.name = data.data[i].name;
                    // html1.postCode = data.data[i].zipCode;
                    // html1.phone = data.data[i].telephone;
                    // html1.prov = data.data[i].provinceName;
                    // html1.city = data.data[i].cityName;
                    // html1.county = data.data[i].districtName;
                    // html1.address = data.data[i].address;
                    // hallAddress[i] = html1;
                }
                $("#hallUI").html(html)
                $("#hand_in_username").text(data.data[0].receiverName)
                $("#hand_in_phone").text(data.data[0].telephone)
                $("#hand_in_address").text(data.data[0].area + data.data[0].address)
                hallId = data.data[0].id
                $("#hallUI>li").click(function (e) {
                    e.stopPropagation()
                    $("#hall").val($(this).text()).attr("code", $(this).attr("code"))
                    $("#hallUI").hide()

                    $("#hand_in_username").text(data.data[$(this).index()].receiverName)
                    $("#hand_in_phone").text(data.data[$(this).index()].telephone)
                    $("#hand_in_address").text(data.data[$(this).index()].area + data.data[$(this).index()].address)
                    //
                    $("#receive_message_username").text(data.data[$(this).index()].receiverName)
                    $("#receive_message_phone").text(data.data[$(this).index()].telephone)
                    $("#receive_message_addr").text(data.data[$(this).index()].area + data.data[$(this).index()].address)

                    receiver_name= data.data[$(this).index()].name;
                    receiver_postCode= data.data[$(this).index()].zipCode;
                    receiver_phone= data.data[$(this).index()].telephone;
                    receiver_prov= data.data[$(this).index()].provinceName;
                    receiver_city= data.data[$(this).index()].cityName;
                    receiver_county= data.data[$(this).index()].districtName;
                    receiver_address= data.data[$(this).index()].address;
                    hallId = data.data[$(this).index()].id

                })
                $("#hall_div").show();
            } else {
                hallId = data.data[0].id

                // console.log(hallId);
                $("#hand_in_username").text(data.data[0].receiverName)
                $("#hand_in_phone").text(data.data[0].telephone)
                $("#hand_in_address").text(data.data[0].area + data.data[0].address)
                //
                $("#receive_message_username").text(data.data[0].receiverName)
                $("#receive_message_phone").text(data.data[0].telephone)
                $("#receive_message_addr").text(data.data[0].area + data.data[0].address)

                receiver_name= data.data[0].name;
                receiver_postCode= data.data[0].zipCode;
                receiver_phone= data.data[0].telephone;
                receiver_prov= data.data[0].provinceName;
                receiver_city= data.data[0].cityName;
                receiver_county= data.data[0].districtName;
                receiver_address= data.data[0].address;

            }


        },
        error: function (err) {
            console.log(err);
        }
    })
}

function getSceneInfo() {
    $.ajax({
        url: basePath() + "/app/fingertips/matter/scene",
        // url:"http://19.202.141.212:8080/sdbst/app/fingertips/matter/scene",
        data: {
            matterId: $("#matterId").text()
        },
        type: "get",
        dataType: "json",
        async:true,
        success: function (data) {
            console.log(data);
            if(data.code == 200){
                // hallId = data.data[0].id
                if (data.data.length>0){
                    var html = ''
                    for (var i = 0; i < data.data.length; i++) {
                        html += '<li code="' + data.data[i].id + '">' + data.data[i].name + '</li>'
                    }
                    $("#scencUI").html(html);
                    material.sceneId = data.data[0].id
                    $("#scencUI>li").click(function (e) {
                        e.stopPropagation()
                        $("#hall").val($(this).text()).attr("code", $(this).attr("code"))
                        $("#hallUI").hide()
                        material.sceneId = data.data[$(this).index()].id
                    })
                    $("#scene_div").show();
                } else {
                    $("#scene_div").hide();
                }
            }
        },
        error: function (err) {
            console.log(err);
        }
    })
}
function initData() {
    //获取证件类型
    getCertificateType()
    //获取大厅信息
    getHallInfo()
    //事项编号
    $("#save-commit-dive>.coding_direct>.label").text("事项编码："+$("#matterCode").text())
    //获取用户资料
    getUserHistory()
    //获取用户默认地址
    getUserAddress()
    //获取办事指引图
    getDetailPic();
}

function getDetailPic(){
    $.ajax({
        url:basePath()+"/app/fingertips/matter/detail",
        // url:"http://19.202.141.200:8080/sdbst/app/fingertips/matter/detail",
        data:{
            id:$("#matterId").text()
        },
        type:"get",
        dataType:"json",
        async:true,
        success:function (data) {
            //办事指引图
            $("#work_guide>div>img").attr("src",data.data.outFlowPics[0].url.replace("http://19.200.93.99", "http://support.shunde.gov.cn:9306"));
        },
        error:function (err) {
            console.log(err);
        }
    })
}

function getUserHistory(){
    $.ajax({
        url: basePath() + "/app/fingertips/findUserHistory",
        data: {
            encPhone:$("#dePhoneEnc").text()
        },
        type: "post",
        dataType: "json",
        async:true,
        success: function (data) {
            if(data.code == 200){
                if(data.data){
                    user_obj = data.data
                    $("#agentCertificateNum").val(data.data.agentCertificateNum)
                    $("#agentName").val(data.data.agentName)
                    $("#agentPhone").val(data.data.agentPhone)

                    // if(data.data.applicantType == "1"){
                    //     $("#base-div input[name=applicant_type]").eq(0).attr("checked","checked")
                    //     $("#applicantName").val(data.data.applicantName)
                    //     $("#agentCertificateNum_applicant").val(data.data.applicantCertificateNum)
                    //     $("#applicantPhone").val(data.data.applicantPhone)
                    //
                    //     $("#username_operate").val(data.data.applicantName)
                    //     $("#phone_operate").val(data.data.applicantPhone)
                    //
                    //     $("#user_contact_username").css("display", "inline-block").text(data.data.agentName);
                    //     $("#user_contact_phone").css("display", "inline").text(data.data.agentPhone);
                    //
                    // }else{
                    //     $("#base-div input[name=applicant_type]").eq(1).attr("checked","checked")
                    //     $("#enterpriseName").val(data.data.enApplicantName)
                    //     $("#applicantCertificateNum").val(data.data.enApplicantCertificateNum)
                    //     $("#applicantPhone_enterprise").val(data.data.enApplicantPhone)
                    //
                    //     $("#user_contact_username_enterprise").css("display", "inline-block").text(data.data.agentName);
                    //     $("#user_contact_phone_enterprise").css("display", "inline").text(data.data.agentPhone);
                    // }
                    //个人办事
                    $("#applicantName").val(data.data.applicantName)
                    $("#agentCertificateNum_applicant").val(data.data.applicantCertificateNum)
                    $("#applicantPhone").val(data.data.applicantPhone)

                    $("#username_operate").val(data.data.applicantName)
                    $("#phone_operate").val(data.data.applicantPhone)

                    $("#user_contact_username").css("display", "inline-block").text(data.data.agentName);
                    $("#user_contact_phone").css("display", "inline").text(data.data.agentPhone);

                    //企业办事
                    $("#enterpriseName").val(data.data.enApplicantName)
                    $("#applicantCertificateNum").val(data.data.enApplicantCertificateNum)
                    $("#applicantPhone_enterprise").val(data.data.enApplicantPhone)

                    $("#user_contact_username_enterprise").css("display", "inline-block").text(data.data.agentName);
                    $("#user_contact_phone_enterprise").css("display", "inline").text(data.data.agentPhone);
                    $("#user_contact_phone_enterprise").css("display", "inline").text(data.data.agentPhone);
                    if(data.data.applicantType == "1"){
                        $("#base-div input[name=applicant_type]").eq(0).attr("checked","checked")
                    }else{
                        $("#base-div input[name=applicant_type]").eq(1).attr("checked","checked")
                    }
                    //联系地址
                    // $("#username_operate").val();
                    // $("#phone_operate").val();
                }else{
                    $("#agentPhone").val($("#dePhone").text())
                    $("#agentCertificateNum").val($("#personCid").text())
                    $("#agentName").val($("#personName").text())
                }
            }else{
                $("#agentPhone").val($("#dePhone").text())
                $("#agentCertificateNum").val($("#personCid").text())
                $("#agentName").val($("#personName").text())
            }
        },
        error: function (err) {
            $("#agentPhone").val($("#dePhone").text())
            $("#agentCertificateNum").val($("#personCid").text())
            $("#agentName").val($("#personName").text())
        }
    })
}

function getCertificateType() {
    mui.showLoading("正在获取信息","div");
    $.ajax({
        url: basePath() + "/app/fingertips/common/certificateTypes",
        // url:"http://19.202.141.212:8080/sdbst/app/fingertips/common/certificateTypes",
        data: {
            applicantType: "1"
        },
        type: "get",
        dataType: "json",
        async:true,
        success: function (data) {
            mui.hideLoading(function(){
                var html = ''
                for (var i = 0; i < data.data.length; i++) {
                    html += '<li code="' + data.data[i].code + '">' + data.data[i].value + '</li>'
                }
                $("#agentCertificateTypeUl").html(html)
                $("#agentCertificateTypeUl_applicant").html(html)
                $("#agentCertificateTypeUl>li").click(function (e) {
                    e.stopPropagation()
                    $("#agentCertificateType").val($(this).text()).attr("code", $(this).attr("code"))
                    $("#agentCertificateTypeUl").hide()
                })
                $("#agentCertificateTypeUl_applicant>li").click(function (e) {
                    e.stopPropagation()
                    $("#agentCertificateType_applicant").val($(this).text()).attr("code", $(this).attr("code"))
                    $("#agentCertificateTypeUl_applicant").hide()
                })
            });
        },
        error: function (err) {
            mui.hideLoading(function(){
                mui.toast("获取信息失败")
                console.log(err);
            });
        }
    })
    $.ajax({
        url: basePath() + "/app/fingertips/common/certificateTypes",
        // url:"http://19.202.141.212:8080/sdbst/app/fingertips/common/certificateTypes",
        data: {
            applicantType: "2"
        },
        type: "get",
        dataType: "json",
        async:true,
        success: function (data) {
            var html = ''
            for (var i = 0; i < data.data.length; i++) {
                html += '<li code="' + data.data[i].code + '" style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">' + data.data[i].value + '</li>'
            }
            $("#agentCertificateTypeUl_enterprise").html(html)
            $("#agentCertificateTypeUl_enterprise>li").click(function (e) {
                e.stopPropagation()
                $("#agentCertificateType_enterprise").val($(this).text()).attr("code", $(this).attr("code"))
                $("#agentCertificateTypeUl_enterprise").hide()
            })
        },
        error: function (err) {
            console.log(err);
        }
    })
}

function getUserAddress() {
    $.ajax({
        url: basePath() + "/app/fingertips/findUserAddressByAgentPhone",
        data: {
            encPhone:$("#dePhoneEnc").text()
        },
        type: "get",
        dataType: "json",
        async:true,
        success: function (data) {
            if(data.code == 200){
                if(data.data) {
                    var location = data.data.provinceName + data.data.cityName + data.data.districtName + data.data.detail
                    $("#location").attr({
                        province: data.data.province,
                        city: data.data.city,
                        district: data.data.district
                    }).val(data.data.provinceName + " " + data.data.cityName + " " + data.data.districtName)
                    $("#postcode_operate").val(data.data.postcode)
                    $("#detail_operate").val(data.data.detail)
                }
                // $("#user_contact_address").css("display", "block").text(location);
                // $(".click_add_address1").hide()
                //
                // $("#user_contact_address_enterprise").css("display", "block").text(location);
                // $(".click_add_address2").hide()

            }
        },
        error: function (err) {
            console.log(err);
        }
    })
}
