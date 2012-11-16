function Recommend() {
	var outRFunction;
	var outAjax = 'AEwebAjax.jsp';
	var ecommerceName = 'jingdong';
	var key = 'an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ';
	var serviceUrl = 'http://www.thinkingtop.com/kaasservice';
	var endUser = '';
	var scheme = '';
	var product = '';
	var outputItemsNum = '';
	var outputQuantitye = '';
	function getXMLRequester(){
		var xmlhttp_request = false;
		try {
			if (window.ActiveXObject) {
				for ( var i = 5; i; i--) {
					try {
						if (i == 2) {
							xmlhttp_request = new ActiveXObject(
									'Microsoft.XMLHTTP');
						} else {
							xmlhttp_request = new ActiveXObject(
									'Msxml2.XMLHTTP.' + i + '.0');
						}
						break;
					} catch (e) {
						xmlhttp_request = false;
					}
				}
			} else if (window.XMLHttpRequest) {
				xmlhttp_request = new XMLHttpRequest();
				if (xmlhttp_request.overrideMimeType) {
					xmlhttp_request.overrideMimeType('text/xml');
				}
			}
		} catch (e) {
			xmlhttp_request = false;
			alert('Sorry your browser version is too low, please update after use.');
		}
		return xmlhttp_request;
	};
	
	var xmlhttp = getXMLRequester();
	
	function requestByPost(data) {
		// alert(xmlhttp);
		var URL = 'http://localhost:8080/kaas-service/services/Service?wsdl';
		xmlhttp.open('POST', URL, false);
		xmlhttp.setRequestHeader('Content-Type', 'text/xml; charset=utf-8');
		xmlhttp.setRequestHeader('SOAPAction',
				'http://dao.wfservice.ws.emolay.com');
		xmlhttp.send(data);
		var str = xmlhttp.responseText;
		var xmlDoc = new DOMParser().parseFromString(str, 'text/xml');
		var result = xmlDoc.getElementsByTagName('result');
		return result;
	};

	function send(id) {
		var url = outAjax + '?time=' + Math.random() + '&ids=' + id;
		if (xmlhttp) {
			xmlhttp.onreadystatechange = callback;
			xmlhttp.open('GET', url, true);
			xmlhttp.send(null);
		//alert(url);
		} else {
			alert('your browser does not support ajax');
			return;
		}
	};

	function callback() {
		//alert("isResult:"+isResult);
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				var xmlDoc = xmlhttp.responseXML;
				outRFunction(xmlDoc);
			} else {
				alert(xmlHttp.statusText);
			}
		}
	};

	function jurisdiction(){
		var data = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="'+serviceUrl+'">';
		data += '<soapenv:Header/>';
		data += '<soapenv:Body>';
		data += '<ser:GetState>';
		data += '<ecommerceName>' + ecommerceName + '</ecommerceName>';
		data += '<KeyString>' + key + '</KeyString>';
		data += '</ser:GetState>';
		data += '</soapenv:Body>';
		data += '</soapenv:Envelope>';
		var result = requestByPost(data);
		var state = result[0].firstChild.nodeValue;
		return state;
	};
	
	function information(_endUser,_product,_scheme,_outputItemsNum,_outputQuantitye,_outRFunction){
		endUser = _endUser;
		product = _product;
		scheme = _scheme;
		outputItemsNum = _outputItemsNum;
		outputQuantitye = _outputQuantitye;
		outRFunction = _outRFunction;
		//alert(endUser+"-"+product+"-"+scheme+"-"+scheme+"-"+outputItemsNum+"-"+outputQuantitye+"-"+outAjax);
		return getRecommend;
	};
	
	function getRecommend() {
		//alert("getRecommend");
		var data = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="'+serviceUrl+'">';
		data += '<soapenv:Header/>';
		data += '<soapenv:Body>';
		data += '<ser:GetRecommends>';
		data += '<ecommerceName>' + ecommerceName + '</ecommerceName>';
		data += '<KeyString>' + key + '</KeyString>';
		data += '<endUser>' + endUser + '</endUser>';
		data += '<scheme>' + scheme + '</scheme>';
		data += '<inputItems>' + product + '</inputItems>';
		data += '<outputItemsNum>' + outputItemsNum + '</outputItemsNum>';
		data += '<outputQuantitye>' + outputQuantitye + '</outputQuantitye>';
		data += '</ser:GetRecommends>';
		data += '</soapenv:Body>';
		data += '</soapenv:Envelope>';
		var result = requestByPost(data);
		var str = [];
		for ( var i = 0; i < result.length; i++) {
			str.push(result[i].firstChild.nodeValue);
		}
		send(str);
		return str;
	};
	
	var state = jurisdiction();
	//alert("state:"+state);
	if(state!=2){
		return false;
	}
	return information;
}
