<#list xImpl>
	<note><title>Changes:</title>
	<#assign odescription=0>
	<#assign ocondition=0>
	<#assign condition=0>
	<#assign cdate=0>
	<#assign cstatus=0>
	<#assign csignature=0>
	<#assign fieldName=0>
	<#assign description=0>
	<#assign oldDescription=0>
	<#assign date=0>
	<#assign status=0>
	<#assign signature=0>
	<#items as k, v>	
		<#if k=="condition">
			<#list v as k1, v1>
				<#if k1=="description">
					<#assign odescription=v1>
				</#if>			
				<#if k1=="original">
					<#assign ocondition=v1>
				</#if>
				<#if k1=="current">
					<#assign condition=v1>
				</#if>
				<#if k1=="date">
					<#assign cdate=v1>
				</#if>
				<#if k1=="status">
					<#assign cstatus=v1>
				</#if>
				<#if k1=="signature">
					<#assign csignature=v1>
				</#if>				
			</#list>
		</#if>
		<#if k=="description">
			<#assign description=v>
		</#if>
		<#if k=="old-description">
			<#assign oldDescription=v>
		</#if>		
		<#if k=="date">
			<#assign date=v>
		</#if>
		<#if k=="status">
			<#assign status=v>
		</#if>
		<#if k=="signature">
			<#assign signature=v>
		</#if>				
	</#items>
	<para>
	    <#if odescription?? && odescription?is_string><para>${md2docbook(odescription)}</para></#if>
		<#if ocondition?? && ocondition?is_string && condition?? && condition?is_string><para>Originally <emphasis>${ocondition}</emphasis> it was changed to <emphasis role="strong">${condition}</emphasis></para><para> <#if cstatus?? && cdate??><#if cstatus??><para>Status: <emphasis role="strong">${cstatus}</emphasis></#if> <#if cdate?? && cdate?is_date>changed on ${cdate?date}</#if></#if></para> </#if>
		<#if csignature?? && csignature?is_string><para>Signature: <emphasis>${csignature}</emphasis></para></#if>
		<#if description?? && description?is_string><para>${md2docbook(description)}</para></#if>
		<#if oldDescription?? && oldDescription?is_string><para><emphasis role="strong">Old description was:</emphasis></para><para>${md2docbook(oldDescription)}</para></#if>
		<#if status?? && status?is_string><para>Status: <emphasis role="strong">${status}</emphasis> <#if date?? && date?is_date>changed on ${date?date}</#if></para></#if>
		<#if signature?? && signature?is_string><para>Signature: <emphasis>${signature}</emphasis></para></#if>
	</para>
	</note>
</#list>
