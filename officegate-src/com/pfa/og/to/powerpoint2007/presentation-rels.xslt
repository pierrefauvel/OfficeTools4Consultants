<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
>

<xsl:template match="/o">
 <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
  <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/slideMaster" Target="slideMasters/slideMaster1.xml" /> 

	  <Relationship Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/slide" Id="rId2" Target="slides/slide1.xml" />
	<xsl:for-each select="o">
	  <Relationship Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/slide"  >
			<xsl:attribute name="Id">rId<xsl:value-of select="position()+2" /></xsl:attribute>
			<xsl:attribute name="Target">slides/slide<xsl:value-of select="position()+1"/>.xml</xsl:attribute>
	  </Relationship>
	</xsl:for-each>

  <Relationship Id="rId7" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/tableStyles" Target="tableStyles.xml">
		<xsl:attribute name="Id">rId<xsl:value-of select="count(o)+6" /></xsl:attribute>
  </Relationship> 
  <Relationship Id="rId6" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme" Target="theme/theme1.xml" > 
		<xsl:attribute name="Id">rId<xsl:value-of select="count(o)+5" /></xsl:attribute>
  </Relationship> 
  <Relationship Id="rId5" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/viewProps" Target="viewProps.xml" > 
		<xsl:attribute name="Id">rId<xsl:value-of select="count(o)+4" /></xsl:attribute>
  </Relationship> 
  <Relationship Id="rId4" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/presProps" Target="presProps.xml" > 
		<xsl:attribute name="Id">rId<xsl:value-of select="count(o)+3" /></xsl:attribute>
  </Relationship> 
  </Relationships>
  
  
</xsl:template>

</xsl:stylesheet>