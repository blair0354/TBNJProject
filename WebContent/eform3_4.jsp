<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="home/head.jsp" %>
<%@ include file="menu_bar.jsp"%>
<jsp:useBean id="obj" class="eform.eform3_4.EFORM3_4" scope="request">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-control" content="no-cache" />
<link rel="stylesheet" href="js/default.css" type="text/css" />
<link rel="stylesheet" href="js/font.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="js/font.css">
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript" src="js/function.js"></script>
<script type="text/javascript" src="js/tablesoft.js"></script>
<script type="text/javascript" src="js/jquery-1.12.1.min.js"></script>
<title><%=session.getAttribute("com_title")%>-申請案件管理清冊</title>
</head>
<script language="JavaScript">

var weekend = [0,6];
var weekendColor = "#e0e0e0";
var fontface = "Arial";
var fontsize = 2;

var gNow = new Date();
var ggWinCal;
isNav = (navigator.appName.indexOf("Netscape") != -1) ? true : false;
isIE = (navigator.appName.indexOf("Microsoft") != -1) ? true : false;

Calendar.Months = ["1月", "2月", "3月", "4月", "5月", "6月",
"7月", "8月", "9月", "10月", "11月", "12月"];

// Non-Leap year Month days..
Calendar.DOMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
// Leap year Month days..
Calendar.lDOMonth = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

function Calendar(p_item, p_WinCal, p_month, p_year, p_format) {
	if ((p_month == null) && (p_year == null))	return;

	if (p_WinCal == null)
		this.gWinCal = ggWinCal;
	else
		this.gWinCal = p_WinCal;
	
	if (p_month == null) {
		this.gMonthName = null;
		this.gMonth = null;
		this.gYearly = true;
	} else {
		this.gMonthName = Calendar.get_month(p_month);
		this.gMonth = new Number(p_month);
		this.gYearly = false;
	}

	this.gYear = p_year;
	this.gFormat = p_format;
	this.gBGColor = "white";
	this.gFGColor = "black";
	this.gTextColor = "black";
	this.gHeaderColor = "226699";
	this.gReturnItem = p_item;
}

Calendar.get_month = Calendar_get_month;
Calendar.get_daysofmonth = Calendar_get_daysofmonth;
Calendar.calc_month_year = Calendar_calc_month_year;
Calendar.print = Calendar_print;

function Calendar_get_month(monthNo) {
	return Calendar.Months[monthNo];
}

function Calendar_get_daysofmonth(monthNo, p_year) {
	/* 
	Check for leap year ..
	1.Years evenly divisible by four are normally leap years, except for... 
	2.Years also evenly divisible by 100 are not leap years, except for... 
	3.Years also evenly divisible by 400 are leap years. 
	*/
	if ((p_year % 4) == 0) {
		if ((p_year % 100) == 0 && (p_year % 400) != 0)
			return Calendar.DOMonth[monthNo];
	
		return Calendar.lDOMonth[monthNo];
	} else
		return Calendar.DOMonth[monthNo];
}

function Calendar_calc_month_year(p_Month, p_Year, incr) {
	/* 
	Will return an 1-D array with 1st element being the calculated month 
	and second being the calculated year 
	after applying the month increment/decrement as specified by 'incr' parameter.
	'incr' will normally have 1/-1 to navigate thru the months.
	*/
	var ret_arr = new Array();
	
	if (incr == -1) {
		// B A C K W A R D
		if (p_Month == 0) {
			ret_arr[0] = 11;
			ret_arr[1] = parseInt(p_Year) - 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) - 1;
			ret_arr[1] = parseInt(p_Year);
		}
	} else if (incr == 1) {
		// F O R W A R D
		if (p_Month == 11) {
			ret_arr[0] = 0;
			ret_arr[1] = parseInt(p_Year) + 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) + 1;
			ret_arr[1] = parseInt(p_Year);
		}
	}
	
	return ret_arr;
}

function Calendar_print() {
	ggWinCal.print();
}

function Calendar_calc_month_year(p_Month, p_Year, incr) {
	/* 
	Will return an 1-D array with 1st element being the calculated month 
	and second being the calculated year 
	after applying the month increment/decrement as specified by 'incr' parameter.
	'incr' will normally have 1/-1 to navigate thru the months.
	*/
	var ret_arr = new Array();
	
	if (incr == -1) {
		// B A C K W A R D
		if (p_Month == 0) {
			ret_arr[0] = 11;
			ret_arr[1] = parseInt(p_Year) - 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) - 1;
			ret_arr[1] = parseInt(p_Year);
		}
	} else if (incr == 1) {
		// F O R W A R D
		if (p_Month == 11) {
			ret_arr[0] = 0;
			ret_arr[1] = parseInt(p_Year) + 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) + 1;
			ret_arr[1] = parseInt(p_Year);
		}
	}
	
	return ret_arr;
}

// This is for compatibility with Navigator 3, we have to create and discard one object before the prototype object exists.
new Calendar();

Calendar.prototype.getMonthlyCalendarCode = function() {
	var vCode = "";
	var vHeader_Code = "";
	var vData_Code = "";
	
	// Begin Table Drawing code here..
	vCode = vCode + "<TABLE BORDER=1 cellpadding=3 cellspacing=0 bordercolorlight=999999 bordercolordark=ffffff class=cb12 BGCOLOR=\"" + this.gBGColor + "\">";
	
	vHeader_Code = this.cal_header();
	vData_Code = this.cal_data();
	vCode = vCode + vHeader_Code + vData_Code;
	
	vCode = vCode + "</TABLE>";
	
	return vCode;
}

Calendar.prototype.show = function() {
	var vCode = "";
	
	this.gWinCal.document.open();

	// Setup the page...
	this.wwrite("<html>");
	this.wwrite("<head><title>Calendar</title>");
	this.wwrite("<link rel=stylesheet href=ie4.css>");
	this.wwrite("</head>");

	this.wwrite("<body topmargin=10 leftmargin=0 bgcolor=eeeeee " + 
		"link=\"" + this.gLinkColor + "\" " + 
		"vlink=\"" + this.gLinkColor + "\" " +
		"alink=\"" + this.gLinkColor + "\" " +
		"text=\"" + this.gTextColor + "\">");
	this.wwriteA("<FONT FACE='" + fontface + "' style=font-size:11pt><B>");
	this.wwriteA( "&nbsp;&nbsp;&nbsp;&nbsp;<font color=666666>" + this.gYear + " 年 " + this.gMonthName + "</font> ");
	this.wwriteA("</B><BR>");

	// Show navigation buttons
	var prevMMYYYY = Calendar.calc_month_year(this.gMonth, this.gYear, -1);
	var prevMM = prevMMYYYY[0];
	var prevYYYY = prevMMYYYY[1];

	var nextMMYYYY = Calendar.calc_month_year(this.gMonth, this.gYear, 1);
	var nextMM = nextMMYYYY[0];
	var nextYYYY = nextMMYYYY[1];
	
	this.wwrite("<TABLE WIDTH='100%' BORDER=1 CELLSPACING=0 CELLPADDING=3 BGCOLOR='#e0e0e0' class=c12><TR><TD ALIGN=center>");
	this.wwrite("<A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', '" + this.gMonth + "', '" + (parseInt(this.gYear)-1) + "', '" + this.gFormat + "'" +
		");" +
		"\">上年<\/A></TD><TD ALIGN=center>");
	this.wwrite("<A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', '" + prevMM + "', '" + prevYYYY + "', '" + this.gFormat + "'" +
		");" +
		"\">上月<\/A></TD><TD ALIGN=center>");
	this.wwrite("<A HREF=\"javascript:window.print();\">Print</A></TD><TD ALIGN=center>");
	this.wwrite("<A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', '" + nextMM + "', '" + nextYYYY + "', '" + this.gFormat + "'" +
		");" +
		"\">下月<\/A></TD><TD ALIGN=center>");
	this.wwrite("<A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', '" + this.gMonth + "', '" + (parseInt(this.gYear)+1) + "', '" + this.gFormat + "'" +
		");" +
		"\">下年<\/A></TD></TR></TABLE>");

	// Get the complete calendar code for the month..
	vCode = this.getMonthlyCalendarCode();
	this.wwrite(vCode);

	this.wwrite("</font></body></html>");
	this.gWinCal.document.close();
}

Calendar.prototype.showY = function() {
	var vCode = "";
	var i;
	var vr, vc, vx, vy;		// Row, Column, X-coord, Y-coord
	var vxf = 285;			// X-Factor
	var vyf = 200;			// Y-Factor
	var vxm = 10;			// X-margin
	var vym;				// Y-margin
	if (isIE)	vym = 75;
	else if (isNav)	vym = 25;
	
	this.gWinCal.document.open();

	this.wwrite("<html>");
	this.wwrite("<head><title>Calendar</title>");
	this.wwrite("<style type='text/css'>\n<!--");
	for (i=0; i<12; i++) {
		vc = i % 3;
		if (i>=0 && i<= 2)	vr = 0;
		if (i>=3 && i<= 5)	vr = 1;
		if (i>=6 && i<= 8)	vr = 2;
		if (i>=9 && i<= 11)	vr = 3;
		
		vx = parseInt(vxf * vc) + vxm;
		vy = parseInt(vyf * vr) + vym;

		this.wwrite(".lclass" + i + " {position:absolute;top:" + vy + ";left:" + vx + ";}");
	}
	this.wwrite("-->\n</style>");
	this.wwrite("</head>");

	this.wwrite("<body " + 
		"link=\"" + this.gLinkColor + "\" " + 
		"vlink=\"" + this.gLinkColor + "\" " +
		"alink=\"" + this.gLinkColor + "\" " +
		"text=\"" + this.gTextColor + "\">");
	this.wwrite("<FONT FACE='" + fontface + "' SIZE=2><B>");
	this.wwrite("Year : " + this.gYear);
	this.wwrite("</B><BR>");

	// Show navigation buttons
	var prevYYYY = parseInt(this.gYear) - 1;
	var nextYYYY = parseInt(this.gYear) + 1;
	
	this.wwrite("<TABLE WIDTH='100%' BORDER=1 CELLSPACING=0 CELLPADDING=0 BGCOLOR='#e0e0e0'><TR><TD ALIGN=center>");
	this.wwrite("[<A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', null, '" + prevYYYY + "', '" + this.gFormat + "'" +
		");" +
		"\" alt='Prev Year'><<<\/A>]</TD><TD ALIGN=center>");
	this.wwrite("[<A HREF=\"javascript:window.print();\">Print</A>]</TD><TD ALIGN=center>");
	this.wwrite("[<A HREF=\"" +
		"javascript:window.opener.Build(" + 
		"'" + this.gReturnItem + "', null, '" + nextYYYY + "', '" + this.gFormat + "'" +
		");" +
		"\">>><\/A>]</TD></TR></TABLE><BR>");

	// Get the complete calendar code for each month..
	var j;
	for (i=11; i>=0; i--) {
		if (isIE)
			this.wwrite("<DIV ID=\"layer" + i + "\" CLASS=\"lclass" + i + "\">");
		else if (isNav)
			this.wwrite("<LAYER ID=\"layer" + i + "\" CLASS=\"lclass" + i + "\">");

		this.gMonth = i;
		this.gMonthName = Calendar.get_month(this.gMonth);
		vCode = this.getMonthlyCalendarCode();
		this.wwrite(this.gMonthName + "/" + this.gYear + "<BR>");
		this.wwrite(vCode);

		if (isIE)
			this.wwrite("</DIV>");
		else if (isNav)
			this.wwrite("</LAYER>");
	}

	this.wwrite("</font><BR></body></html>");
	this.gWinCal.document.close();
}

Calendar.prototype.wwrite = function(wtext) {
	this.gWinCal.document.writeln(wtext);
}

Calendar.prototype.wwriteA = function(wtext) {
	this.gWinCal.document.write(wtext);
}

Calendar.prototype.cal_header = function() {
	var vCode = "";
	
	vCode = vCode + "<TR>";
	vCode = vCode + "<TD WIDTH='14%'><FONT  FACE='" + fontface + "' COLOR='red'><b>Sun</b></FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%'><FONT  FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'><b>Mon</b></FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%'><FONT  FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'><b>Tue</b></FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%'><FONT  FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'><b>Wed</b></FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%'><FONT  FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'><b>Thu</b></FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%'><FONT  FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'><b>Fri</b></FONT></TD>";
	vCode = vCode + "<TD WIDTH='16%'><FONT  FACE='" + fontface + "' COLOR='000099'><b>Sat</b></FONT></TD>";
	vCode = vCode + "</TR>";
	
	return vCode;
}

Calendar.prototype.cal_data = function() {
	var vDate = new Date();
	vDate.setDate(1);
	vDate.setMonth(this.gMonth);
	vDate.setFullYear(this.gYear);

	var vFirstDay=vDate.getDay();
	var vDay=1;
	var vLastDay=Calendar.get_daysofmonth(this.gMonth, this.gYear);
	var vOnLastDay=0;
	var vCode = "";

	/*
	Get day for the 1st of the requested month/year..
	Place as many blank cells before the 1st day of the month as necessary. 
	*/

	vCode = vCode + "<TR>";
	for (i=0; i<vFirstDay; i++) {
		vCode = vCode + "<TD WIDTH='14%'" + this.write_weekend_string(i) + "><FONT FACE='" + fontface + "'>&nbsp;</FONT></TD>";
	}

	// Write rest of the 1st week
	for (j=vFirstDay; j<7; j++) {
		vCode = vCode + "<TD WIDTH='14%'" + this.write_weekend_string(j) + "><FONT FACE='" + fontface + "'>" + 
			"<A HREF='#' " + 
				"onClick=\"self.opener.document." + this.gReturnItem + ".value='" + 
				this.format_data(vDay) + 
				"';window.close();\">" + 
				this.format_day(vDay) + 
			"</A>" + 
			"</FONT></TD>";
		vDay=vDay + 1;
	}
	vCode = vCode + "</TR>";

	// Write the rest of the weeks
	for (k=2; k<7; k++) {
		vCode = vCode + "<TR>";

		for (j=0; j<7; j++) {
			vCode = vCode + "<TD WIDTH='14%'" + this.write_weekend_string(j) + "><FONT FACE='" + fontface + "'>" + 
				"<A HREF='#' " + 
					"onClick=\"self.opener.document." + this.gReturnItem + ".value='" + 
					this.format_data(vDay) + 
					"';window.close();\">" + 
				this.format_day(vDay) + 
				"</A>" + 
				"</FONT></TD>";
			vDay=vDay + 1;

			if (vDay > vLastDay) {
				vOnLastDay = 1;
				break;
			}
		}

		if (j == 6)
			vCode = vCode + "</TR>";
		if (vOnLastDay == 1)
			break;
	}
	
	// Fill up the rest of last week with proper blanks, so that we get proper square blocks
	for (m=1; m<(7-j); m++) {
		if (this.gYearly)
			vCode = vCode + "<TD WIDTH='14%'" + this.write_weekend_string(j+m) + 
			"><FONT SIZE='2' FACE='" + fontface + "' COLOR='gray'>&nbsp;</FONT></TD>";
		else
			vCode = vCode + "<TD WIDTH='14%'" + this.write_weekend_string(j+m) + 
			"><FONT SIZE='2' FACE='" + fontface + "' COLOR='gray'>" + m + "</FONT></TD>";
	}
	
	return vCode;
}

Calendar.prototype.format_day = function(vday) {
	var vNowDay = gNow.getDate();
	var vNowMonth = gNow.getMonth();
	var vNowYear = gNow.getFullYear();

	if (vday == vNowDay && this.gMonth == vNowMonth && this.gYear == vNowYear)
		return ("<FONT COLOR=\"RED\"><B>" + vday + "</B></FONT>");
	else
		return (vday);
}

Calendar.prototype.write_weekend_string = function(vday) {
	var i;

	// Return special formatting for the weekend day.
	for (i=0; i<weekend.length; i++) {
		if (vday == weekend[i])
			return (" BGCOLOR=\"" + weekendColor + "\"");
	}
	
	return "";
}

Calendar.prototype.format_data = function(p_day) {
	var vData;
	var vMonth = 1 + this.gMonth;
	vMonth = (vMonth.toString().length < 2) ? "0" + vMonth : vMonth;
	var vMon = Calendar.get_month(this.gMonth).substr(0,3).toUpperCase();
	var vFMon = Calendar.get_month(this.gMonth).toUpperCase();
	var vY4 = new String(this.gYear) - 1911;
	vY4 = vY4.toString().length < 3 ? "0" + vY4 : vY4;
	var vY2 = new String(this.gYear.substr(2,2));
	var vDD = (p_day.toString().length < 2) ? "0" + p_day : p_day;

	switch (this.gFormat) {
		case "MM\/DD\/YYYY" :
			vData = vY4+"\/"+vMonth + "\/" + vDD ;
			break;
		case "MM\/DD\/YY" :
			vData = vMonth + "\/" + vDD + "\/" + vY2;
			break;
		case "MM-DD-YYYY" :
			vData = vMonth + "-" + vDD + "-" + vY4;
			break;
		case "MM-DD-YY" :
			vData = vMonth + "-" + vDD + "-" + vY2;
			break;

		case "DD\/MON\/YYYY" :
			vData = vDD + "\/" + vMon + "\/" + vY4;
			break;
		case "DD\/MON\/YY" :
			vData = vDD + "\/" + vMon + "\/" + vY2;
			break;
		case "DD-MON-YYYY" :
			vData = vDD + "-" + vMon + "-" + vY4;
			break;
		case "DD-MON-YY" :
			vData = vDD + "-" + vMon + "-" + vY2;
			break;

		case "DD\/MONTH\/YYYY" :

			vData = vDD + "\/" + vFMon + "\/" + vY4;
			break;
		case "DD\/MONTH\/YY" :
			vData = vDD + "\/" + vFMon + "\/" + vY2;
			break;
		case "DD-MONTH-YYYY" :
			vData = vDD + "-" + vFMon + "-" + vY4;
			break;
		case "DD-MONTH-YY" :
			vData = vDD + "-" + vFMon + "-" + vY2;
			break;

		case "DD\/MM\/YYYY" :
			vData = vDD + "\/" + vMonth + "\/" + vY4;
			break;
		case "DD\/MM\/YY" :
			vData = vDD + "\/" + vMonth + "\/" + vY2;
			break;
		case "DD-MM-YYYY" :
			vData = vDD + "-" + vMonth + "-" + vY4;
			break;
		case "DD-MM-YY" :
			vData = vDD + "-" + vMonth + "-" + vY2;
			break;

		default :
			vData = vMonth + "\/" + vDD + "\/" + vY4;
	}

	return vData;
}

function Build(p_item, p_month, p_year, p_format) {
	var p_WinCal = ggWinCal;
	gCal = new Calendar(p_item, p_WinCal, p_month, p_year, p_format);

	// Customize your Calendar here..
	gCal.gBGColor="white";
	gCal.gLinkColor="black";
	gCal.gTextColor="black";
	gCal.gHeaderColor="000000";

	// Choose appropriate show function
	if (gCal.gYearly)	gCal.showY();
	else	gCal.show();
}

function show_calendar() {
	/* 
		p_month : 0-11 for Jan-Dec; 12 for All Months.
		p_year	: 4-digit year
		p_format: Date format (mm/dd/yyyy, dd/mm/yy, ...)
		p_item	: Return Item.
	*/

	p_item = arguments[0];
	if (arguments[1] == null)
		p_month = new String(gNow.getMonth());
	else
		p_month = arguments[1];
	if (arguments[2] == "" || arguments[2] == null)
		p_year = new String(gNow.getFullYear().toString());
	else
		p_year = arguments[2];
	if (arguments[3] == null)
		p_format = "MM/DD/YYYY";
	else
		p_format = arguments[3];

	vWinCal = window.open("", "Calendar", 
		"width=350,height=300,status=no,resizable=no,top=200,left=200");
	vWinCal.opener = self;
	ggWinCal = vWinCal;

	Build(p_item, p_month, p_year, p_format);
}
/*
Yearly Calendar Code Starts here
*/
function show_yearly_calendar(p_item, p_year, p_format) {
	// Load the defaults..
	if (p_year == null || p_year == "")
		p_year = new String(gNow.getFullYear().toString());
	if (p_format == null || p_format == "")
		p_format = "MM/DD/YYYY";

	var vWinCal = window.open("", "Calendar", "scrollbars=yes");
	vWinCal.opener = self;
	ggWinCal = vWinCal;

	Build(p_item, null, p_year, p_format);
}
</script>
<script language="javascript">
	function selectCityNo(txtcity_no){

		$.ajax({
			type: "post",
			url: "eform3_3_ajax_1.jsp"
					+ "?txtcity_no=" + txtcity_no
					+ "&unit=" + $("#unit").val(),
			datatype: "text",
			async: false,
			success: function(response){
				if(response.length > 10){
					unit = response.substr(8);
					$("#txtunit option").remove();
					$("#txtunit").append(unit);
				}
			}
		});
	}
	

	function checkSubmit(){

		form1.action = "eform3_4_qry.jsp";
		form1.method = "post";
		form1.target = "_blank";
		form1.submit();
	}
	

	function setName(obj,changName){
		var index = obj.selectedIndex;
		var name = obj.options[index].text;
		if(name.indexOf("-") > 0){
			name = name.substring(name.indexOf("-")+1);
		}
		$("#" + changName).val(name);
	}
</script>
<%
String unit = (String)session.getAttribute("unit");
obj.setUnit(unit);
%>
<body background="images/main_bg.jpg" topmargin="0">
	<FORM Method=post name="form1" onsubmit="return checkSubmit();">
	<input type="hidden" id="unit" name="unit" value="<%=obj.getUnit()%>">
		<table border="0" width=637 cellspacing="0" cellpadding="0">
			<tr align="center" valign="middle">
				<td colspan="3"><font size="4" face="標楷體" color="#FF9900">申請案件管理清冊
				</font>
					<hr size="1" color="#008000" width="660" align="left"></td>
			</tr>
			<tr>
				<td width="245">查詢縣市：
					<select style="background: ffffcc" id="txtcity_no" name="txtcity_no" onchange="selectCityNo(this.value);setName(this,'txtcity_name');">
						<%=obj.getCityOption(unit)%>
					</select>
					<input type="hidden" id="txtcity_name" name="txtcity_name">
				</td>
				<td width="280">事務所:
					<select style="background: ffffcc" id="txtunit" name="txtunit" onchange="setName(this,'txtunit_name');">
						<%=obj.getOffice(unit)%>
					</select>
					<input type="hidden" id="txtunit_name" name="txtunit_name">
				</td>
				<td width="175" align="center" bgcolor="#FF00FF"><font
					color="#FFFFFF">申請案件管理清冊</font></td>
			</tr>
			<tr>
				<td colspan="2">查詢狀態： 
			        <select name=txtqry_msg style="background:ffffcc">
			          <option value="全部">全部</option>
			          <option value="已完成查詢">已完成查詢</option>
			          <option value="尚未完成查詢">尚未完成查詢</option>
			        </select>
		        </td>
				<td align="center"><font color="#333333" size="2"> <input
						type=button value="製表" class=Button onClick="checkSubmit();"
						id=button1 name=button1> &nbsp; <INPUT name="reset"
						type="reset" tabIndex="4" title="清除" value="清除" />
				</font></td>
			</tr>
			<tr>
				<td colspan="2">查詢日期： 
			        <input name="txtqry_date_start" maxlength="10" size="8" value="<%=obj.getToday()%>"> 
			        <a href="javascript:show_calendar('form1.txtqry_date_start');" onmouseover="window.status='開啟對話框';return true;" onmouseout="window.status='';return true;"> 
			        <img src="images/calendar.gif" border=0></a> ～ 
			        <input name="txtqry_date_end" maxlength="10" size="8" value="<%=obj.getToday()%>"> 
			        <a href="javascript:show_calendar('form1.txtqry_date_end');" onmouseover="window.status='開啟對話框';return true;" onmouseout="window.status='';return true;"> 
			        <img src="images/calendar.gif" border=0></a>
        		</td>
			</tr>
		</table>
		<hr size="1" color="#008000" width="660" align="left">
		</td>
		</tr>
		</table>
	</FORM>
</body>
</html>