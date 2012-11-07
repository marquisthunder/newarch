function Recommend() {
	
	
	function jurisdiction(){
		return 2;
	}

	var ecommerceName = 'jingdong';
	var key = 'an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ';
	var endUser;
	var scheme;
	var product;
	var outputItemsNum;
	var outputQuantitye;
	var outRFunction;
	var outAjax;

	function information(_endUser,_product,_scheme,_outputItemsNum,_outputQuantitye,_outAjax,_outRFunction){
		endUser = _endUser;
		product = _product;
		scheme = _scheme;
		outputItemsNum = _outputItemsNum;
		outputQuantitye = _outputQuantitye;
		outAjax = _outAjax;
		outRFunction = _outRFunction;
		var bb = "outs";
		//outRFunction(bb);
		alert("111");
		alert(ecommerceName+"-"+key+"-"+endUser+"-"+product+"-"+scheme+"-"+scheme+"-"+outputItemsNum+"-"+outputQuantitye+"-"+outAjax);
		return getRecommend;
	}
	
	function getRecommend() {
		alert("getRecommend");
	}
	
	var state = jurisdiction();
	if(state!=2){
		return false;
	}
	alert(state==2);
	
	
	return information;
}
