package org.apifever.raml.support;

public enum HttpStatusResponse {
	HTTP_100(100,"Continue","RFC7231, Section 6.2.1", "https://tools.ietf.org/html/rfc7231"),
	HTTP_101(101,"Switching Protocols","RFC7231, Section 6.2.2", "https://tools.ietf.org/html/rfc7231"),
	HTTP_102(102,"Processing","RFC2518", "https://tools.ietf.org/html/rfc2518"),
	HTTP_200(200,"OK","RFC7231, Section 6.3.1", "https://tools.ietf.org/html/rfc7231"),
	HTTP_201(201,"Created","RFC7231, Section 6.3.2", "https://tools.ietf.org/html/rfc7231"),
	HTTP_202(202,"Accepted","RFC7231, Section 6.3.3", "https://tools.ietf.org/html/rfc7231"),
	HTTP_203(203,"Non-Authoritative Information","RFC7231, Section 6.3.4", "https://tools.ietf.org/html/rfc7231"),
	HTTP_204(204,"No Content","RFC7231, Section 6.3.5", "https://tools.ietf.org/html/rfc7231"),
	HTTP_205(205,"Reset Content","RFC7231, Section 6.3.6", "https://tools.ietf.org/html/rfc7231"),
	HTTP_206(206,"Partial Content","RFC7233, Section 4.1", "https://tools.ietf.org/html/rfc7233"),
	HTTP_207(207,"Multi-Status","RFC4918", "https://tools.ietf.org/html/rfc4918"),
	HTTP_208(208,"Already Reported","RFC5842", "https://tools.ietf.org/html/rfc5842"),
	HTTP_226(226,"IM Used","RFC3229", "https://tools.ietf.org/html/rfc3229"),
	HTTP_300(300,"Multiple Choices","RFC7231, Section 6.4.1", "https://tools.ietf.org/html/rfc7231"),
	HTTP_301(301,"Moved Permanently","RFC7231, Section 6.4.2", "https://tools.ietf.org/html/rfc7231"),
	HTTP_302(302,"Found","RFC7231, Section 6.4.3", "https://tools.ietf.org/html/rfc7231"),
	HTTP_303(303,"See Other","RFC7231, Section 6.4.4", "https://tools.ietf.org/html/rfc7231"),
	HTTP_304(304,"Not Modified","RFC7232, Section 4.1", "https://tools.ietf.org/html/rfc7232"),
	HTTP_305(305,"Use Proxy","RFC7231, Section 6.4.5", "https://tools.ietf.org/html/rfc7231"),
	HTTP_306(306,"(Unused)","RFC7231, Section 6.4.6", "https://tools.ietf.org/html/rfc7231"),
	HTTP_307(307,"Temporary Redirect","RFC7231, Section 6.4.7", "https://tools.ietf.org/html/rfc7231"),
	HTTP_308(308,"Permanent Redirect","RFC7538", "https://tools.ietf.org/html/rfc7538"),
	HTTP_400(400,"Bad Request","RFC7231, Section 6.5.1", "https://tools.ietf.org/html/rfc7231"),
	HTTP_401(401,"Unauthorized","RFC7235, Section 3.1", "https://tools.ietf.org/html/rfc7235"),
	HTTP_402(402,"Payment Required","RFC7231, Section 6.5.2", "https://tools.ietf.org/html/rfc7231"),
	HTTP_403(403,"Forbidden","RFC7231, Section 6.5.3", "https://tools.ietf.org/html/rfc7231"),
	HTTP_404(404,"Not Found","RFC7231, Section 6.5.4", "https://tools.ietf.org/html/rfc7231"),
	HTTP_405(405,"Method Not Allowed","RFC7231, Section 6.5.5", "https://tools.ietf.org/html/rfc7231"),
	HTTP_406(406,"Not Acceptable","RFC7231, Section 6.5.6", "https://tools.ietf.org/html/rfc7231"),
	HTTP_407(407,"Proxy Authentication Required","RFC7235, Section 3.2", "https://tools.ietf.org/html/rfc7235"),
	HTTP_408(408,"Request Timeout","RFC7231, Section 6.5.7", "https://tools.ietf.org/html/rfc7231"),
	HTTP_409(409,"Conflict","RFC7231, Section 6.5.8", "https://tools.ietf.org/html/rfc7231"),
	HTTP_410(410,"Gone","RFC7231, Section 6.5.9", "https://tools.ietf.org/html/rfc7231"),
	HTTP_411(411,"Length Required","RFC7231, Section 6.5.10", "https://tools.ietf.org/html/rfc7231"),
	HTTP_412(412,"Precondition Failed","RFC7232, Section 4.2", "https://tools.ietf.org/html/rfc7232"),
	HTTP_413(413,"Payload Too Large","RFC7231, Section 6.5.11", "https://tools.ietf.org/html/rfc7231"),
	HTTP_414(414,"URI Too Long","RFC7231, Section 6.5.12", "https://tools.ietf.org/html/rfc7231"),
	HTTP_415(415,"Unsupported Media Type","RFC7231, Section 6.5.13, RFC7694, Section 3", "https://tools.ietf.org/html/rfc7231"),
	HTTP_416(416,"Range Not Satisfiable","RFC7233, Section 4.4", "https://tools.ietf.org/html/rfc7233"),
	HTTP_417(417,"Expectation Failed","RFC7231, Section 6.5.14", "https://tools.ietf.org/html/rfc7231"),
	HTTP_421(421,"Misdirected Request","RFC7540, Section 9.1.2", "https://tools.ietf.org/html/rfc7540"),
	HTTP_422(422,"Unprocessable Entity","RFC4918", "https://tools.ietf.org/html/rfc4918"),
	HTTP_423(423,"Locked","RFC4918", "https://tools.ietf.org/html/rfc4918"),
	HTTP_424(424,"Failed Dependency","RFC4918", "https://tools.ietf.org/html/rfc4918"),
	HTTP_426(426,"Upgrade Required","RFC7231, Section 6.5.15", "https://tools.ietf.org/html/rfc7231"),
	HTTP_428(428,"Precondition Required","RFC6585", "https://tools.ietf.org/html/rfc6585"),
	HTTP_429(429,"Too Many Requests","RFC6585", "https://tools.ietf.org/html/rfc6585"),
	HTTP_431(431,"Request Header Fields Too Large","RFC6585", "https://tools.ietf.org/html/rfc6585"),
	HTTP_451(451,"Unavailable For Legal Reasons","RFC7725", "https://tools.ietf.org/html/rfc7725"),
	HTTP_500(500,"Internal Server Error","RFC7231, Section 6.6.1", "https://tools.ietf.org/html/rfc7231"),
	HTTP_501(501,"Not Implemented","RFC7231, Section 6.6.2", "https://tools.ietf.org/html/rfc7231"),
	HTTP_502(502,"Bad Gateway","RFC7231, Section 6.6.3", "https://tools.ietf.org/html/rfc7231"),
	HTTP_503(503,"Service Unavailable","RFC7231, Section 6.6.4", "https://tools.ietf.org/html/rfc7231"),
	HTTP_504(504,"Gateway Timeout","RFC7231, Section 6.6.5", "https://tools.ietf.org/html/rfc7231"),
	HTTP_505(505,"HTTP Version Not Supported","RFC7231, Section 6.6.6", "https://tools.ietf.org/html/rfc7231"),
	HTTP_506(506,"Variant Also Negotiates","RFC2295", "https://tools.ietf.org/html/rfc2295"),
	HTTP_507(507,"Insufficient Storage","RFC4918", "https://tools.ietf.org/html/rfc4918"),
	HTTP_508(508,"Loop Detected","RFC5842", "https://tools.ietf.org/html/rfc5842"),
	HTTP_510(510,"Not Extended","RFC2774", "https://tools.ietf.org/html/rfc2774"),
	HTTP_511(511,"Network Authentication Required","RFC6585", "https://tools.ietf.org/html/rfc6585");
	
	private int statusCode;
	private String description;
	private String title;
	private String href;
	
	private HttpStatusResponse(int statusCode, String description, String title, String href) {
		this.statusCode = statusCode;
		this.description = description;
		this.title = title;
		this.href = href;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

	public String getHref() {
		return href;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(80);
		sb
			.append(statusCode)
			.append(" - ")
			.append(description);
		return sb.toString();
	}
}
