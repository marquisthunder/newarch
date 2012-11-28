<form id="alipaysubmit" name="alipaysubmit"
	action="https://mapi.alipay.com/gateway.do?_input_charset=utf-8"
	method="post">
	<input type="hidden" name="body" value="NO DESCRIBE" /><input
		type="hidden" name="logistics_type" value="EXPRESS" /><input
		type="hidden" name="logistics_fee" value="0.00" /><input type="hidden"
		name="subject" value="dhu2" /><input type="hidden" name="sign_type"
		value="MD5" /><input type="hidden" name="receive_address" value="东华大街" /><input
		type="hidden" name="receive_phone" value="0663154554" /><input
		type="hidden" name="receive_name" value="梁少" /><input type="hidden"
		name="notify_url"
		value="https://dhu-chinesejie.rhcloud.com/create_partner_trade_by_buyer-JAVA-UTF-8/notify_url.jsp" /><input
		type="hidden" name="out_trade_no" value="2012112812395564" /><input
		type="hidden" name="return_url"
		value="https://dhu-chinesejie.rhcloud.com/create_partner_trade_by_buyer-JAVA-UTF-8/return_url.jsp" /><input
		type="hidden" name="sign" value="28832f09a94963564058a7486740360c" /><input
		type="hidden" name="_input_charset" value="utf-8" /><input
		type="hidden" name="price" value="0.01" /><input type="hidden"
		name="service" value="create_partner_trade_by_buyer" /><input
		type="hidden" name="receive_mobile" value="15201967268" /><input
		type="hidden" name="quantity" value="1" /><input type="hidden"
		name="partner" value="2088302206374094" /><input type="hidden"
		name="seller_email" value="15201967267" /><input type="hidden"
		name="receive_zip" value="202620" /><input type="hidden"
		name="logistics_payment" value="SELLER_PAY" /><input type="hidden"
		name="payment_type" value="1" /><input type="hidden" name="show_url"
		value="https://dhu-chinesejie.rhcloud.com" /><input type="submit"
		value="确认" style="display: none;">
</form>
<script>
	document.forms['alipaysubmit'].submit();
</script>
