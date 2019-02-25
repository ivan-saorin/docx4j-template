<#list xImpl>
	<note><title>Changes:</title>
	<#assign odescription=0>
	<#assign ocondition=0>
	<#assign condition=0>
	<#assign cdate1=0>
	<#assign cstatus1=0>
	<#assign csignature1=0>
	<#assign cdate2=0>
	<#assign cstatus2=0>
	<#assign csignature2=0>
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
					<#assign cdate1=v1>
				</#if>
				<#if k1=="status">
					<#assign cstatus1=v1>
				</#if>
				<#if k1=="signature">
					<#assign csignature1=v1>
				</#if>
				<#if k1=="date2">
					<#assign cdate2=v1>
				</#if>
				<#if k1=="status2">
					<#assign cstatus2=v1>
				</#if>
				<#if k1=="signature2">
					<#assign csignature2=v1>
				</#if>				
				<#if k1=="date3">
					<#assign cdate3=v1>
				</#if>
				<#if k1=="status3">
					<#assign cstatus3=v1>
				</#if>
				<#if k1=="signature3">
					<#assign csignature3=v1>
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
		<#if ocondition?? && ocondition?is_string && condition?? && condition?is_string><para>Originally <emphasis>${ocondition}</emphasis> it was changed to <emphasis role="strong">${condition}</emphasis></para></#if>
		<#if csignature1?? && csignature1?is_string><para>Signature: <emphasis>${csignature1}</emphasis> <#if cstatus1??>(<emphasis role="strong">${cstatus1}</emphasis>)</#if>, <#if cdate1?? && cdate1?is_date>${cdate1?date}</#if></para></#if>
		<#if csignature2?? && csignature2?is_string><para>Signature: <emphasis>${csignature2}</emphasis> <#if cstatus2??>(<emphasis role="strong">${cstatus2}</emphasis>)</#if>, <#if cdate2?? && cdate2?is_date>${cdate2?date}</#if></para></#if>
		<#if csignature3?? && csignature3?is_string><para>Signature: <emphasis>${csignature3}</emphasis> <#if cstatus3??>(<emphasis role="strong">${cstatus3}</emphasis>)</#if>, <#if cdate3?? && cdate3?is_date>${cdate3?date}</#if></para></#if>
		<#if description?? && description?is_string><para>${md2docbook(description)}</para></#if>
		<#if oldDescription?? && oldDescription?is_string><para><emphasis role="strong">Old description was:</emphasis></para><para>${md2docbook(oldDescription)}</para></#if>
		<#if status?? && status?is_string><para>Status: <emphasis role="strong">${status}</emphasis> <#if date?? && date?is_date>changed on ${date?date}</#if></para></#if>
		<#if signature?? && signature?is_string><para>Signature: <emphasis>${signature}</emphasis></para></#if>
	</para>
	</note>
</#list>
