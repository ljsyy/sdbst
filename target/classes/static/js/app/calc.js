
$(document).ready(function(){
	/* 说明展开、隐藏 */
	$(".list .white-click").click(function(){
		var tip = $(this).text();
		if(tip == "<<<"){
			$(this).parent().prev().css("display","none");
			$(this).text(">>>");
		}else if(tip == ">>>"){
			$(this).parent().prev().css("display","-webkit-inline-box");
			$(this).text("<<<");
		}else{
			return;
		}
	});
	
	/* 文化程度 */
	$("#edu").change(function(){
	    var edu=Number($("#edu").val());
	    $("#eduScore").text(edu);
	    sum();
	});
	
	/* 职业资格或专业技术职称 */
	$("#tech").change(function(){
	    var tech=Number($("#tech").val());
	    $("#techScore").text(tech);
	    sum();
	});
	
	/* 房产情况 */
	$("#house").change(function(){
	    if($("#house").prop("checked") == true){ 
			var house = 80;
		}
		if($("#house").prop("checked") == false){ 
			var house = 0;
		}
	    $("#houseScore").text(house);
	    sum();
	});
	
	/* 居住证 */
	var live;
	$("#isLive").change(function(){
	    if($("#isLive").prop("checked") == true){ 
			$("#isLiveHide").css("display","-webkit-inline-box");
		}
		if($("#isLive").prop("checked") == false){ 
			$("#isLiveHide").css("display","none");
			live = 0;
			$("#liveScore").text(live);
		}
		sum();
	});
	$("#liveYear").change(function(){
		var liveYear=Number($("#liveYear").val());
		live = liveYear * 5;
	    $("#liveScore").text(live);
	    sum();
	});
	
	/* 年龄 */
	$("#olds").change(function(){
	    var olds=Number($("#olds").val());
	    var oldsScore;
	    if(olds >= 50){
	    	oldsScore = 5;
	    }else if(olds >= 0 && olds < 50){
	    	oldsScore = 5 + (50 - olds);
	    }else{
	    	return
	    }
	    if(oldsScore > 15){
	    	oldsScore = 15;
	    }
	    $("#oldsScore").text(oldsScore);
	    sum();
	});
	
	/* 婚姻状况 */
	$("#marry").change(function(){
	    if($("#marry").prop("checked") == true){ 
			var marry = 10;
		}
		if($("#marry").prop("checked") == false){ 
			var marry = 0;
		}
	    $("#marryScore").text(marry);
	    sum();
	});
	
	/* 特殊职业、技术人员 */
	var spec;
	var hardYear;
	var proYear;
	$("#isHard,#isPro,#hardYear,#proYear").change(function(){
	    if($("#isHard").prop("checked") == true){ 
			$("#isHardHide").css("display","-webkit-inline-box");
			hardYear = Number($("#hardYear").val());
		}
	    if($("#isHard").prop("checked") == false){ 
	    	$("#isHardHide").css("display","none");
	    	hardYear = 0;
	    }
	    if($("#isPro").prop("checked") == true){ 
			$("#isProHide").css("display","-webkit-inline-box");
			proYear = Number($("#proYear").val());
		}
		if($("#isPro").prop("checked") == false){ 
			$("#isProHide").css("display","none");
			proYear = 0;
		}
		spec = (hardYear + proYear) * 4;
		if(spec > 20){
			spec = 20;
		}
	    $("#specScore").text(spec);
	    sum();
	});
	
	/* 发明专利 */
	var invent;
	var nation;
	var practical;
	var appearance;
	$("#isNation,#isPractical,#isAppearance,#nation,#practical,#appearance").change(function(){
	    if($("#isNation").prop("checked") == true){ 
			$("#isNationHide").css("display","-webkit-inline-box");
			nation = Number($("#nation").val());
		}
	    if($("#isNation").prop("checked") == false){ 
	    	$("#isNationHide").css("display","none");
	    	nation = 0;
	    }
	    if($("#isPractical").prop("checked") == true){ 
			$("#isPracticalHide").css("display","-webkit-inline-box");
			practical = Number($("#practical").val());
		}
		if($("#isPractical").prop("checked") == false){ 
			$("#isPracticalHide").css("display","none");
			practical = 0;
		}
		if($("#isAppearance").prop("checked") == true){ 
			$("#isAppearanceHide").css("display","-webkit-inline-box");
			appearance = Number($("#appearance").val());
		}
		if($("#isAppearance").prop("checked") == false){ 
			$("#isAppearanceHide").css("display","none");
			appearance = 0;
		}
		invent = nation + practical + appearance;
	    $("#inventScore").text(invent);
	    sum();
	});
	
	/*志愿者*/
	var volun;
	$("#volunHour").change(function(){
		var volunHour=Number($("#volunHour").val());
		volun = parseInt(volunHour/50) * 3;
		if(volun > 30){
			volun = 30;
		}
	    $("#volunScore").text(volun);
	    sum();
	});
	
	/* 投资 */
	$("#invest").change(function(){
	    var invest = Number($("#invest").val());
	    $("#investScore").text(invest);
	    sum();
	});
	
	/* 纳税 */
	var tax;
	$("#taxP,#taxC").change(function(){
	    var taxP = Number($("#taxP").val());
	    var taxC = Number($("#taxC").val());
	    taxP = parseInt(taxP/500);
	    taxC = parseInt(taxC/10000);
	    tax = taxP + taxC;
	    $("#taxScore").text(tax);
	    sum();
	});
	
	/*卫生防疫*/
	var health;
	var vaccine;
	var bodyCheck;
	var healthBook;
	$("#isVaccine,#isBodyCheck,#isHealthBook").change(function(){
	    if($("#isVaccine").prop("checked") == true){ 
	    	vaccine = 3;
		}
		if($("#isVaccine").prop("checked") == false){ 
			vaccine = 0;
		}
		if($("#isBodyCheck").prop("checked") == true){ 
			bodyCheck = 1;
		}
		if($("#isBodyCheck").prop("checked") == false){ 
			bodyCheck = 0;
		}
		if($("#isHealthBook").prop("checked") == true){ 
			healthBook = 1;
		}
		if($("#isHealthBook").prop("checked") == false){ 
			healthBook = 0;
		}
		health = vaccine + bodyCheck + healthBook;
		$("#healthScore").text(health);
		sum();
	});
	
	/*计划生育证*/
	var familyPlanBook;
	$("#isFamilyPlanBook").change(function(){
	    if($("#isFamilyPlanBook").prop("checked") == true){ 
	    	familyPlanBook = 5;
		}
		if($("#isFamilyPlanBook").prop("checked") == false){ 
			familyPlanBook = 0;
		}
		$("#familyPlanBookScore").text(familyPlanBook);
		sum();
	});
	
	/* 承诺表 */
	var commit;
	$("#isCommit").change(function(){
	    if($("#isCommit").prop("checked") == true){ 
			$("#isCommitHide").css("display","-webkit-inline-box");
		}
		if($("#isCommit").prop("checked") == false){ 
			$("#isCommitHide").css("display","none");
			commit = 0;
			$("#commitScore").text(commit);
		}
		sum();
	});
	$("#commitNum").change(function(){
		var commitNum = Number($("#commitNum").val());
		commit = commitNum * 5;
	    $("#commitScore").text(commit);
	    sum();
	});
	
	/* 基础教育 */
	$("#basicEdu").change(function(){
		var basicEdu = Number($("#basicEdu").val());
	    if(basicEdu == 0){
	    	$("#basicEduScore").text(basicEdu);
	    }else{
	    	var eduYear = Number($("#eduYear").val());
		    var basicEduScore = basicEdu * eduYear;
		    $("#basicEduScore").text(basicEduScore);
	    }
	    sum();
	});
	$("#eduYear").change(function(){
		var basicEdu = Number($("#basicEdu").val());
	    var eduYear = Number($("#eduYear").val());
	    var basicEduScore = basicEdu * eduYear;
	    $("#basicEduScore").text(basicEduScore);
	    sum();
	});
	
	/*遵守计划生育*/
	var reward;
	var policy;
	var processed;
	$("#isPolicy,#isProcessed").change(function(){
	    if($("#isPolicy").prop("checked") == true){ 
	    	policy = 30;
		}
		if($("#isPolicy").prop("checked") == false){ 
			policy = 0;
		}
		if($("#isProcessed").prop("checked") == true){ 
			processed = 10;
		}
		if($("#isProcessed").prop("checked") == false){ 
			processed = 0;
		}
		reward = policy + processed;
		$("#rewardScore").text(reward);
		sum();
	});
	
	/*自定义*/
	$("#custom").change(function(){
		var custom = Number($("#custom").val());
		if(custom > 20){
			custom = 20;
		}
	    $("#customScore").text(custom);
	    sum();
	});
	
	/*参保*/
	var iOldS;
	var iMedS;
	var iDoS;
	var iHurtS;
	var iBirthS;
	var outScore;
	var inScore;
	$("#oOldS,#oMedS,#oDoS,#oHurtS,#oBirthS,#iOldS,#iMedS,#iDoS,#iHurtS,#iBirthS,#isInS").change(function(){
		if($("#isInS").prop("checked") == true){ 
			$("#isInHide").css("display","-webkit-inline-box");
			iOldS = Number($("#iOldS").val());
			iMedS = Number($("#iMedS").val());
			iDoS = Number($("#iDoS").val());
			iHurtS = Number($("#iHurtS").val());
			iBirthS = Number($("#iBirthS").val());
			inScore = iOldS + iMedS + iDoS + iHurtS + iBirthS;
		}
		if($("#isInS").prop("checked") == false){ 
			$("#isInHide").css("display","none");
			iOldS = 0;
			iMedS = 0;
			iDoS = 0;
			iHurtS = 0;
			iBirthS = 0;
			inScore = iOldS + iMedS + iDoS + iHurtS + iBirthS;
		}
		var oOldS = Number($("#oOldS").val());
		var oMedS = Number($("#oMedS").val());
		var oDoS = Number($("#oDoS").val());
		var oHurtS = Number($("#oHurtS").val());
		var oBirthS = Number($("#oBirthS").val());
		outScore = oOldS + oMedS + oDoS + oHurtS + oBirthS;
		if(outScore > 50){
			outScore = 50;
		}
		if(inScore > 50){
			inScore = 50;
		}
		
		var security = outScore + inScore;
		$("#securityScore").text(security);
		sum();
	});
	
	/*表彰*/
	var commendX;
	var commendD;
	var inCommendX;
	var inCommendD;
	var commendXScore;
	var commendDScore;
	var inCommendXScore;
	var inCommendDScore;
	var XDScore;
	var inXDScore;
	$("#commendX,#commendD,#isInGet,#inCommendX,#inCommendD").change(function(){
		if($("#isInGet").prop("checked") == true){ 
			$("#isInXHide,#isInDHide").css("display","-webkit-inline-box");
			inCommendX = Number($("#inCommendX").val());
			inCommendD = Number($("#inCommendD").val());
			
		}
		if($("#isInGet").prop("checked") == false){ 
			$("#isInXHide,#isInDHide").css("display","none");
			inCommendX = 0;
			inCommendD = 0;
		}
		commendX = Number($("#commendX").val());
		commendD = Number($("#commendD").val());
		commendXScore = commendX * 30;
		commendDScore = commendD * 60;
		inCommendXScore = inCommendX * 10;
		inCommendDScore = inCommendD * 20;
		if(commendXScore > 60){
			commendXScore = 60;
		}
		if(commendDScore > 120){
			commendDScore = 120;
		}
		if(inCommendXScore > 20){
			inCommendXScore = 20;
		}
		if(inCommendDScore > 20){
			inCommendDScore = 40;
		}
		var commend = commendXScore + commendDScore + inCommendXScore + inCommendDScore;
		$("#commendScore").text(commend);
		sum();
	});
	
	/*获奖*/
	var QPOne;
	var QPTwo;
	var QPThree;
	var SPOne;
	var SPTwo;
	var SPThree;
	var SSPOne;
	var SSPTwo;
	var SSPThree;
	var GPOne;
	var GPTwo;
	var GPThree;
	$("#isQP,#isSP,#isSSP,#isGP,#QPOne,#QPTwo,#QPThree,#SPOne,#SPTwo,#SPThree,#SSPOne,#SSPTwo,#SSPThree,#GPOne,#GPTwo,#GPThree").change(function(){
		if($("#isQP").prop("checked") == true){ 
			$("#isQPHide").css("display","-webkit-inline-box");
			QPOne = Number($("#QPOne").val());
			QPTwo = Number($("#QPTwo").val());
			QPThree = Number($("#QPThree").val());
		}
		if($("#isQP").prop("checked") == false){ 
			$("#isQPHide").css("display","none");
			QPOne = 0;
			QPTwo = 0;
			QPThree = 0;
		}
		if($("#isSP").prop("checked") == true){ 
			$("#isSPHide").css("display","-webkit-inline-box");
			SPOne = Number($("#SPOne").val());
			SPTwo = Number($("#SPTwo").val());
			SPThree = Number($("#SPThree").val());
		}
		if($("#isSP").prop("checked") == false){ 
			$("#isSPHide").css("display","none");
			SPOne = 0;
			SPTwo = 0;
			SPThree = 0;
		}
		if($("#isSSP").prop("checked") == true){ 
			$("#isSSPHide").css("display","-webkit-inline-box");
			SSPOne = Number($("#SSPOne").val());
			SSPTwo = Number($("#SSPTwo").val());
			SSPThree = Number($("#SSPThree").val());
		}
		if($("#isSSP").prop("checked") == false){ 
			$("#isSSPHide").css("display","none");
			SSPOne = 0;
			SSPTwo = 0;
			SSPThree = 0;
		}
		if($("#isGP").prop("checked") == true){ 
			$("#isGPHide").css("display","-webkit-inline-box");
			GPOne = Number($("#GPOne").val());
			GPTwo = Number($("#GPTwo").val());
			GPThree = Number($("#GPThree").val());
		}
		if($("#isGP").prop("checked") == false){ 
			$("#isGPHide").css("display","none");
			GPOne = 0;
			GPTwo = 0;
			GPThree = 0;
		}
		
		var prize = QPOne * 75 + QPTwo * 65 + QPThree * 60 + SPOne * 95 + SPTwo * 85 + SPThree * 80 + SSPOne * 120 + SSPTwo * 110 + SSPThree * 100 + GPOne * 150 + GPTwo * 140 + GPThree * 130;
		$("#prizeScore").text(prize);
		sum();
	});
	
	/*无偿献血*/
	var bloodML;
	var voluner;
	var bone;
	var bloodMLScore;
	var bloodScore;
	$("#isBlood,#isVolun,#isBone,#bloodML").change(function(){
		if($("#isBlood").prop("checked") == true){ 
			$("#isBloodHide").css("display","-webkit-inline-box");
			bloodML = Number($("#bloodML").val());
		}
		if($("#isBlood").prop("checked") == false){ 
			$("#isBloodHide").css("display","none");
			bloodML = 0;
		}
		if($("#isVolun").prop("checked") == true){ 
			voluner = 5;
		}
		if($("#isVolun").prop("checked") == false){ 
			voluner = 0;
		}
		if($("#isBone").prop("checked") == true){ 
			bone = 50;
		}
		if($("#isBone").prop("checked") == false){ 
			bone = 0;
		}
		bloodMLScore = parseInt(bloodML/200) * 3;
		if(bloodMLScore > 30){
			bloodMLScore = 30;
		}
		bloodScore = bloodMLScore + voluner + bone;
	    $("#bloodScore").text(bloodScore);
	    sum();
	});
	
	/*公积金*/
	var fundMonth;
	var fundMonthScore;
	var openFund;
	$("#isOpenFund,#isPayFund,#fundMonth").change(function(){
		if($("#isPayFund").prop("checked") == true){ 
			$("#isFundHide").css("display","-webkit-inline-box");
			fundMonth = Number($("#fundMonth").val());
		}
		if($("#isPayFund").prop("checked") == false){ 
			$("#isFundHide").css("display","none");
			fundMonth = 0;
		}
		if($("#isOpenFund").prop("checked") == true){ 
			openFund = 3;
		}
		if($("#isOpenFund").prop("checked") == false){ 
			openFund = 0;
		}
		fundMonthScore = fundMonth * 0.2;
		if(fundMonthScore > 12){
			fundMonthScore = 12;
		}
		var fund = fundMonthScore + openFund;
		$("#fundScore").text(fund);
		sum();
	});
	
	/*节育*/
	var YJYear;
	var SH;
	var JZ;
	$("#isSH,#isJZ,#isYJ,#YJYear").change(function(){
		if($("#isYJ").prop("checked") == true){ 
			$("#isYJHide").css("display","-webkit-inline-box");
			YJYear = Number($("#YJYear").val());
		}
		if($("#isYJ").prop("checked") == false){ 
			$("#isYJHide").css("display","none");
			YJYear = 0;
		}
		if($("#isSH").prop("checked") == true){ 
			SH = 8;
		}
		if($("#isSH").prop("checked") == false){ 
			SH = 0;
		}
		if($("#isJZ").prop("checked") == true){ 
			JZ = 15;
		}
		if($("#isJZ").prop("checked") == false){ 
			JZ = 0;
		}
		var birthControl = YJYear + SH + JZ;
		$("#birthControlScore").text(birthControl);
		sum();
	});

	/*犯罪*/
	var drug;
	var detention;
	var crim;
	var drugScore;
	var detentionScore;
	$("#isDrug,#isDetention,#isCrim,#drug,#detention").change(function(){
		if($("#isDrug").prop("checked") == true){ 
			$("#isDrugHide").css("display","-webkit-inline-box");
			drug = Number($("#drug").val());
		}
		if($("#isDrug").prop("checked") == false){ 
			$("#isDrugHide").css("display","none");
			drug = 0;
		}
		if($("#isDetention").prop("checked") == true){ 
			$("#isDetentionHide").css("display","-webkit-inline-box");
			detention = Number($("#detention").val());
		}
		if($("#isDetention").prop("checked") == false){ 
			$("#isDetentionHide").css("display","none");
			detention = 0;
		}
		if($("#isCrim").prop("checked") == true){ 
			crim = -100;
		}
		if($("#isCrim").prop("checked") == false){ 
			crim = 0;
		}
		drugScore = drug * (-50);
		detentionScore = detention * (-15);
		var crime = drugScore + detentionScore + crim;
		$("#crimeScore").text(crime);
		sum();
	});

	
});

/*总分*/
function sum(){
	//alert()
	var sumScore = Number($("#eduScore").text()) + Number($("#techScore").text()) + Number($("#securityScore").text()) + Number($("#houseScore").text()) + Number($("#liveScore").text()) + Number($("#oldsScore").text()) + Number($("#marryScore").text()) + Number($("#specScore").text()) + Number($("#inventScore").text()) + Number($("#commendScore").text()) + Number($("#prizeScore").text()) + Number($("#volunScore").text()) + Number($("#bloodScore").text()) + Number($("#investScore").text()) + Number($("#taxScore").text()) + Number($("#healthScore").text()) + Number($("#fundScore").text()) + Number($("#familyPlanScore").text()) + Number($("#birthControlScore").text()) + Number($("#commitScore").text()) + Number($("#basicEduScore").text()) + Number($("#rewardScore").text()) + Number($("#crimeScore").text()) + Number($("#customScore").text());
	$("#sumScore1").text(sumScore);
	$("#sumScore2").text(sumScore);
	$("#sumScore3").text(sumScore);
}