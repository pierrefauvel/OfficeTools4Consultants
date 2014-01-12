<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0"
>

<xsl:template match="/o">
<Properties
	xmlns="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties"
	xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
	<TotalTime>5</TotalTime>
	<Words>12</Words>
	<Application>Microsoft Office PowerPoint</Application>
	<PresentationFormat>Affichage Ã  l'Ã©cran (4:3)</PresentationFormat>
	<Paragraphs>9</Paragraphs>
	<Slides><xsl:value-of select="count(o)+1"/></Slides>
	<Notes>0</Notes>
	<HiddenSlides>0</HiddenSlides>
	<MMClips>0</MMClips>
	<ScaleCrop>false</ScaleCrop>
	<HeadingPairs>
		<vt:vector size="4" baseType="variant">
			<vt:variant>
				<vt:lpstr>ThÃ¨me</vt:lpstr>
			</vt:variant>
			<vt:variant>
				<vt:i4>1</vt:i4>
			</vt:variant>
			<vt:variant>
				<vt:lpstr>Titres des diapositives</vt:lpstr>
			</vt:variant>
			<vt:variant>
				<vt:i4><xsl:value-of select="count(o)+1" /></vt:i4>
			</vt:variant>
		</vt:vector>
	</HeadingPairs>
	<TitlesOfParts>
		<vt:vector size="3" baseType="lpstr">
			<xsl:attribute name="size"><xsl:value-of select="count(o)+2"/></xsl:attribute>
			<vt:lpstr>ThÃ¨me Office</vt:lpstr>
			<vt:lpstr><xsl:value-of select="./@text" /></vt:lpstr>
			<xsl:for-each select="o">
				<vt:lpstr><xsl:value-of select="./@text"/></vt:lpstr>
			</xsl:for-each>
		</vt:vector>
	</TitlesOfParts>
	<LinksUpToDate>false</LinksUpToDate>
	<SharedDoc>false</SharedDoc>
	<HyperlinksChanged>false</HyperlinksChanged>
	<AppVersion>12.0000</AppVersion>
</Properties>

</xsl:template>

</xsl:stylesheet>