<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="Classes.Table"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<title>ANASAYFA</title>
</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
	function callServlet() {
		$("#frmId").submit();
		alert('Başarılı');

		document.location.href = "index.jsp";
		tablolariGetir();
	}

	function tablolariGetir() {

		$.get("TabloGetir", function(responseJson) {
			var $select = $("#dropdownlist");
			$select.find("option").remove();
			$.each(responseJson, function(index, tablo) {
				$("<option>").val(tablo.tabloAdi).text(tablo.tabloAdi)
						.appendTo($select);
			});

		});
	}

	function kolonlariGetir(tabloAdi) {

		var $divKolon = $("#divKolon");
		var $divKolonDegeri = $("#divKolonDegeri");

		$('#divKolon').html('');
		$('#divKolonDegeri').html('');
		//$('#records_table').html('');
		var trHTML = '';
		$.get("KolonGetir", {
			tabloAdi : tabloAdi
		}, function(responseJson) {
			$.each(responseJson, function(index, kolon) {
				trHTML += '<tr class="kolonSatir"><td>' + kolon.columnName
						+ '</td><td id="2""><input type="text" class ="kolonDeger" id ='+kolon.columnName+'></input></td><td></tr>';
			});
			
			 $('#records_table').append(trHTML);
		});
	}
	
	function degerEkle(){
		
		var columnValueList = [];
		
		$("tr.kolonSatir").each(function() {
		    var kolonDeger = $(this).find("input.kolonDeger").val();
		    var kolonName = $(this).find("input.kolonDeger").attr('id');
		    
		    var item = {
					columnName :kolonName ,
					columnValue : kolonDeger
				};

		    columnValueList.push(item);
		});
		
		$.post("KolonGetir",{
			tabloAdi : $("#seciliTabloAdi").val(),
			kolonDegerListesi : JSON.stringify(columnValueList)			
		}, function(responseJson) {
			
		});
	}

	function loadPage() {
		tablolariGetir();

		var columnList = new Array();
		sessionStorage.setItem('columnList', JSON.stringify(columnList));
	}
	window.onload = loadPage;
	function ekle() {
		var item = {
			columnName : document.getElementById("txtKolon").value,
			dataType : document.getElementById("txtVeri").value
		};
		if (item.columnName == "") {
			alert('Kolon Gir');
			return;
		}
		if (item.dataType == "") {
			alert('Tip Gir');
			return;
		}
		addColumn(item);
		var btn = document.createElement("TEXT");
		$("#columnList").val(sessionStorage.getItem('columnList'));

		btn.innerHTML = item.columnName + " - " + item.dataType + "<br>";
		document.body.appendChild(btn);

	}

	function addColumn(object) {
		if (JSON.parse(sessionStorage.getItem('columnList')).length == 0) {

			var columnList = [];
			columnList.push(object);
			sessionStorage.setItem('columnList', JSON.stringify(columnList));

		} else {
			var columnList = JSON.parse(sessionStorage.getItem('columnList'));

			columnList.push(object);
			sessionStorage.setItem('columnList', JSON.stringify(columnList));
		}
	};

	function onChangeDropDown(sel) {
		$("#seciliTabloAdi").val(sel.value);

		kolonlariGetir(sel.value);
	}
</script>

<body>
	<form name="frmDB" id="frmId" action="Servlet" accept-charset="UTF-8"
		method="post">
		<table border="1" width="700" height="250" align="center">
			<tr align="center">
				<td colspan="2">Tablo Adı :</td>
				<td colspan="3"><input type="text" id="txtTable"
					name="txtTable"></td>
			</tr>
			<tr align="center">
				<td>Kolon Adı :</td>
				<td><input type="text" id="txtKolon" name="txtKolon"></td>
				<td>Veri Tipi :</td>
				<td><input type="text" id="txtVeri" name="txtVeri"></td>
				<td><input type="button" id="btnKlnEkle" name="btnKlnEkle"
					value="KOLON EKLE" onclick="ekle()"></td>
			</tr>
			<tr>

				<td colspan="5" align="center"><input type="button" id="kayit"
					name="kayit" value="KAYIT" onclick="callServlet()"></td>
			</tr>
		</table>
		<input type="text" id="columnList" name="columnList" hidden="true">
	</form>

	<form name="frm2" id="frm2" accept-charset="UTF-8" method="post">
		<table border="1" width="500" height="250" align="center">
			<tr align="center">
				<td>Tablo Adı :</td>
				<td><select id="dropdownlist" name="dropdownlist"
					onchange="onChangeDropDown(this);">


				</select></td>
			</tr>
			<tr align="center">
				<td colspan="2"><table id="records_table" border="1">
						<tr>
							<th>Kolon Adı</th>
							<th>Kolon Değeri</th>
						</tr>
					</table></td>

			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" id="kayit2"
					name="kayit2" value="KAYIT" onClick="degerEkle()"></td>

			</tr>
		</table>

		<input type="text" name="inpt"></input> <input type="text" name="inpt"></input>


		<input type="text" id="seciliTabloAdi" name="seciliTabloAdi"
			hidden="true">
	</form>
</body>
</html>
