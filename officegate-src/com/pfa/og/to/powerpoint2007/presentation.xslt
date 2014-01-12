<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/o">
		<p:presentation
			xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"
			xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
			xmlns:p="http://schemas.openxmlformats.org/presentationml/2006/main"
			saveSubsetFonts="1">
			<p:sldMasterIdLst>
				<p:sldMasterId id="2147483648" r:id="rId1" />
			</p:sldMasterIdLst>
			<p:sldIdLst>

	  <p:sldId id="256" r:id="rId2" />
	<xsl:for-each select="o">
	  <p:sldId >
			<xsl:attribute name="id"><xsl:value-of select="position()+256" /></xsl:attribute>
			<xsl:attribute name="r:id">rId<xsl:value-of select="position()+2" /></xsl:attribute>
	  </p:sldId>
	</xsl:for-each>

			</p:sldIdLst>
			<p:sldSz cx="9144000" cy="6858000" type="screen4x3" />
			<p:notesSz cx="6858000" cy="9144000" />
			<p:defaultTextStyle>
				<a:defPPr>
					<a:defRPr lang="fr-FR" />
				</a:defPPr>
				<a:lvl1pPr marL="0" algn="l" defTabSz="914400" rtl="0"
					eaLnBrk="1" latinLnBrk="0" hangingPunct="1">
					<a:defRPr sz="1800" kern="1200">
						<a:solidFill>
							<a:schemeClr val="tx1" />
						</a:solidFill>
						<a:latin typeface="+mn-lt" />
						<a:ea typeface="+mn-ea" />
						<a:cs typeface="+mn-cs" />
					</a:defRPr>
				</a:lvl1pPr>
				<a:lvl2pPr marL="457200" algn="l" defTabSz="914400" rtl="0"
					eaLnBrk="1" latinLnBrk="0" hangingPunct="1">
					<a:defRPr sz="1800" kern="1200">
						<a:solidFill>
							<a:schemeClr val="tx1" />
						</a:solidFill>
						<a:latin typeface="+mn-lt" />
						<a:ea typeface="+mn-ea" />
						<a:cs typeface="+mn-cs" />
					</a:defRPr>
				</a:lvl2pPr>
				<a:lvl3pPr marL="914400" algn="l" defTabSz="914400" rtl="0"
					eaLnBrk="1" latinLnBrk="0" hangingPunct="1">
					<a:defRPr sz="1800" kern="1200">
						<a:solidFill>
							<a:schemeClr val="tx1" />
						</a:solidFill>
						<a:latin typeface="+mn-lt" />
						<a:ea typeface="+mn-ea" />
						<a:cs typeface="+mn-cs" />
					</a:defRPr>
				</a:lvl3pPr>
				<a:lvl4pPr marL="1371600" algn="l" defTabSz="914400" rtl="0"
					eaLnBrk="1" latinLnBrk="0" hangingPunct="1">
					<a:defRPr sz="1800" kern="1200">
						<a:solidFill>
							<a:schemeClr val="tx1" />
						</a:solidFill>
						<a:latin typeface="+mn-lt" />
						<a:ea typeface="+mn-ea" />
						<a:cs typeface="+mn-cs" />
					</a:defRPr>
				</a:lvl4pPr>
				<a:lvl5pPr marL="1828800" algn="l" defTabSz="914400" rtl="0"
					eaLnBrk="1" latinLnBrk="0" hangingPunct="1">
					<a:defRPr sz="1800" kern="1200">
						<a:solidFill>
							<a:schemeClr val="tx1" />
						</a:solidFill>
						<a:latin typeface="+mn-lt" />
						<a:ea typeface="+mn-ea" />
						<a:cs typeface="+mn-cs" />
					</a:defRPr>
				</a:lvl5pPr>
				<a:lvl6pPr marL="2286000" algn="l" defTabSz="914400" rtl="0"
					eaLnBrk="1" latinLnBrk="0" hangingPunct="1">
					<a:defRPr sz="1800" kern="1200">
						<a:solidFill>
							<a:schemeClr val="tx1" />
						</a:solidFill>
						<a:latin typeface="+mn-lt" />
						<a:ea typeface="+mn-ea" />
						<a:cs typeface="+mn-cs" />
					</a:defRPr>
				</a:lvl6pPr>
				<a:lvl7pPr marL="2743200" algn="l" defTabSz="914400" rtl="0"
					eaLnBrk="1" latinLnBrk="0" hangingPunct="1">
					<a:defRPr sz="1800" kern="1200">
						<a:solidFill>
							<a:schemeClr val="tx1" />
						</a:solidFill>
						<a:latin typeface="+mn-lt" />
						<a:ea typeface="+mn-ea" />
						<a:cs typeface="+mn-cs" />
					</a:defRPr>
				</a:lvl7pPr>
				<a:lvl8pPr marL="3200400" algn="l" defTabSz="914400" rtl="0"
					eaLnBrk="1" latinLnBrk="0" hangingPunct="1">
					<a:defRPr sz="1800" kern="1200">
						<a:solidFill>
							<a:schemeClr val="tx1" />
						</a:solidFill>
						<a:latin typeface="+mn-lt" />
						<a:ea typeface="+mn-ea" />
						<a:cs typeface="+mn-cs" />
					</a:defRPr>
				</a:lvl8pPr>
				<a:lvl9pPr marL="3657600" algn="l" defTabSz="914400" rtl="0"
					eaLnBrk="1" latinLnBrk="0" hangingPunct="1">
					<a:defRPr sz="1800" kern="1200">
						<a:solidFill>
							<a:schemeClr val="tx1" />
						</a:solidFill>
						<a:latin typeface="+mn-lt" />
						<a:ea typeface="+mn-ea" />
						<a:cs typeface="+mn-cs" />
					</a:defRPr>
				</a:lvl9pPr>
			</p:defaultTextStyle>
		</p:presentation>
</xsl:template>

</xsl:stylesheet>