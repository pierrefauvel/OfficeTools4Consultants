<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0"
>

<xsl:template match="/o">

<office:document-content
	xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"
	xmlns:style="urn:oasis:names:tc:opendocument:xmlns:style:1.0"
	xmlns:table="urn:oasis:names:tc:opendocument:xmlns:table:1.0"
	xmlns:draw="urn:oasis:names:tc:opendocument:xmlns:drawing:1.0"
	xmlns:fo="urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0"
	xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:dc="http://purl.org/dc/elements/1.1/"
	xmlns:meta="urn:oasis:names:tc:opendocument:xmlns:meta:1.0"
	xmlns:number="urn:oasis:names:tc:opendocument:xmlns:datastyle:1.0"
	xmlns:presentation="urn:oasis:names:tc:opendocument:xmlns:presentation:1.0"
	xmlns:svg="urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0"
	xmlns:chart="urn:oasis:names:tc:opendocument:xmlns:chart:1.0"
	xmlns:dr3d="urn:oasis:names:tc:opendocument:xmlns:dr3d:1.0" xmlns:math="http://www.w3.org/1998/Math/MathML"
	xmlns:form="urn:oasis:names:tc:opendocument:xmlns:form:1.0"
	xmlns:script="urn:oasis:names:tc:opendocument:xmlns:script:1.0"
	xmlns:ooo="http://openoffice.org/2004/office" xmlns:ooow="http://openoffice.org/2004/writer"
	xmlns:oooc="http://openoffice.org/2004/calc" xmlns:dom="http://www.w3.org/2001/xml-events"
	xmlns:xforms="http://www.w3.org/2002/xforms" xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:smil="urn:oasis:names:tc:opendocument:xmlns:smil-compatible:1.0"
	xmlns:anim="urn:oasis:names:tc:opendocument:xmlns:animation:1.0"
	xmlns:rpt="http://openoffice.org/2005/report" xmlns:of="urn:oasis:names:tc:opendocument:xmlns:of:1.2"
	xmlns:xhtml="http://www.w3.org/1999/xhtml" xmlns:grddl="http://www.w3.org/2003/g/data-view#"
	xmlns:officeooo="http://openoffice.org/2009/office"
	xmlns:field="urn:openoffice:names:experimental:ooo-ms-interop:xmlns:field:1.0"
	office:version="1.2" grddl:transformation="http://docs.oasis-open.org/office/1.2/xslt/odf2rdf.xsl">
	<office:scripts />
	<office:automatic-styles>
		<style:style style:name="dp1" style:family="drawing-page">
			<style:drawing-page-properties
				presentation:background-visible="true"
				presentation:background-objects-visible="true" draw:fill="solid"
				draw:fill-color="#ffffff" presentation:display-footer="false"
				presentation:display-page-number="false"
				presentation:display-date-time="false" />
		</style:style>
		<style:style style:name="dp2" style:family="drawing-page">
			<style:drawing-page-properties
				presentation:display-header="true" presentation:display-footer="true"
				presentation:display-page-number="false"
				presentation:display-date-time="true" />
		</style:style>
		<style:style style:name="gr1" style:family="graphic">
			<style:graphic-properties style:protect="size" />
		</style:style>
		<style:style style:name="pr1" style:family="presentation"
			style:parent-style-name="Master1-Layout1-title-Diapositive-de-titre-title">
			<style:graphic-properties draw:stroke="none"
				draw:fill="none" draw:textarea-horizontal-align="center"
				draw:textarea-vertical-align="middle" draw:auto-grow-height="false"
				draw:auto-grow-width="false" fo:padding-top="0.127cm"
				fo:padding-bottom="0.127cm" fo:padding-left="0.254cm"
				fo:padding-right="0.254cm" fo:wrap-option="wrap" />
		</style:style>
		<style:style style:name="pr2" style:family="presentation"
			style:parent-style-name="Master1-Layout1-title-Diapositive-de-titre-subtitle">
			<style:graphic-properties draw:stroke="none"
				draw:fill="none" draw:textarea-horizontal-align="center"
				draw:textarea-vertical-align="top" draw:auto-grow-height="false"
				draw:auto-grow-width="false" fo:padding-top="0.127cm"
				fo:padding-bottom="0.127cm" fo:padding-left="0.254cm"
				fo:padding-right="0.254cm" fo:wrap-option="wrap" />
		</style:style>
		<style:style style:name="pr3" style:family="presentation"
			style:parent-style-name="Master1-Layout1-title-Diapositive-de-titre-notes">
			<style:graphic-properties draw:fill-color="#ffffff"
				fo:min-height="13.114cm" />
		</style:style>
		<style:style style:name="pr4" style:family="presentation"
			style:parent-style-name="Master1-Layout2-obj-Titre-et-contenu-title">
			<style:graphic-properties draw:stroke="none"
				draw:fill="none" draw:textarea-horizontal-align="center"
				draw:textarea-vertical-align="middle" draw:auto-grow-height="false"
				draw:auto-grow-width="false" fo:padding-top="0.127cm"
				fo:padding-bottom="0.127cm" fo:padding-left="0.254cm"
				fo:padding-right="0.254cm" fo:wrap-option="wrap" />
		</style:style>
		<style:style style:name="pr5" style:family="presentation"
			style:parent-style-name="Master1-Layout2-obj-Titre-et-contenu-outline1">
			<style:graphic-properties draw:stroke="none"
				draw:fill="none" draw:textarea-horizontal-align="left"
				draw:textarea-vertical-align="top" draw:auto-grow-height="false"
				draw:auto-grow-width="false" fo:padding-top="0.127cm"
				fo:padding-bottom="0.127cm" fo:padding-left="0.254cm"
				fo:padding-right="0.254cm" fo:wrap-option="wrap" />
		</style:style>
		<style:style style:name="pr6" style:family="presentation"
			style:parent-style-name="Master1-Layout2-obj-Titre-et-contenu-notes">
			<style:graphic-properties draw:fill-color="#ffffff"
				fo:min-height="13.365cm" />
		</style:style>
		<style:style style:name="P1" style:family="paragraph">
			<style:paragraph-properties
				fo:margin-left="0cm" fo:margin-right="0cm" fo:margin-top="0cm"
				fo:margin-bottom="0cm" fo:line-height="100%" fo:text-align="center"
				fo:text-indent="0cm" style:punctuation-wrap="hanging"
				style:writing-mode="lr-tb">
				<style:tab-stops />
			</style:paragraph-properties>
		</style:style>
		<style:style style:name="P2" style:family="paragraph">
			<style:paragraph-properties
				style:writing-mode="lr-tb" style:font-independent-line-spacing="true" />
		</style:style>
		<style:style style:name="P3" style:family="paragraph">
			<style:paragraph-properties
				fo:margin-left="0cm" fo:margin-right="0cm" fo:margin-top="0.282cm"
				fo:margin-bottom="0cm" fo:line-height="100%" fo:text-align="center"
				fo:text-indent="0cm" style:punctuation-wrap="hanging"
				style:writing-mode="lr-tb">
				<style:tab-stops />
			</style:paragraph-properties>
		</style:style>
		<style:style style:name="P4" style:family="paragraph">
			<style:paragraph-properties
				fo:margin-left="0.953cm" fo:margin-right="0cm" fo:margin-top="0.282cm"
				fo:margin-bottom="0cm" fo:line-height="100%" fo:text-align="start"
				fo:text-indent="-0.953cm" style:punctuation-wrap="hanging"
				style:writing-mode="lr-tb">
				<style:tab-stops />
			</style:paragraph-properties>
		</style:style>
		<style:style style:name="P5" style:family="paragraph">
			<style:paragraph-properties
				fo:margin-left="2.064cm" fo:margin-right="0cm" fo:margin-top="0.247cm"
				fo:margin-bottom="0cm" fo:line-height="100%" fo:text-align="start"
				fo:text-indent="-0.953cm" style:punctuation-wrap="hanging"
				style:writing-mode="lr-tb">
				<style:tab-stops />
			</style:paragraph-properties>
		</style:style>
		<style:style style:name="T1" style:family="text">
			<style:text-properties fo:color="#000000"
				style:text-line-through-style="none" style:text-position="0% 100%"
				fo:font-family="Arial" fo:font-size="44pt" fo:letter-spacing="normal"
				fo:language="fr" fo:country="FR" fo:font-style="normal"
				style:text-underline-style="none" fo:font-weight="normal"
				style:text-underline-mode="continuous" style:text-overline-mode="continuous"
				style:text-line-through-mode="continuous" style:letter-kerning="false"
				style:font-size-asian="44pt" style:font-style-asian="normal"
				style:font-weight-asian="normal" style:font-size-complex="44pt"
				style:font-style-complex="normal" style:font-weight-complex="normal" />
		</style:style>
		<style:style style:name="T2" style:family="text">
			<style:text-properties fo:color="#000000"
				style:text-line-through-style="none" style:text-position="0% 100%"
				fo:font-family="Arial" fo:font-size="32pt" fo:letter-spacing="normal"
				fo:language="fr" fo:country="FR" fo:font-style="normal"
				style:text-underline-style="none" fo:font-weight="normal"
				style:text-underline-mode="continuous" style:text-overline-mode="continuous"
				style:text-line-through-mode="continuous" style:letter-kerning="false"
				style:font-size-asian="32pt" style:font-style-asian="normal"
				style:font-weight-asian="normal" style:font-size-complex="32pt"
				style:font-style-complex="normal" style:font-weight-complex="normal" />
		</style:style>
		<style:style style:name="T3" style:family="text">
			<style:text-properties fo:color="#000000"
				style:text-line-through-style="none" style:text-position="0% 100%"
				fo:font-family="Arial" fo:font-size="28pt" fo:letter-spacing="normal"
				fo:language="fr" fo:country="FR" fo:font-style="normal"
				style:text-underline-style="none" fo:font-weight="normal"
				style:text-underline-mode="continuous" style:text-overline-mode="continuous"
				style:text-line-through-mode="continuous" style:letter-kerning="false"
				style:font-size-asian="28pt" style:font-style-asian="normal"
				style:font-weight-asian="normal" style:font-size-complex="28pt"
				style:font-style-complex="normal" style:font-weight-complex="normal" />
		</style:style>
		<text:list-style style:name="L1">
			<text:list-level-style-bullet
				text:level="1" text:bullet-char="●">
				<style:list-level-properties />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="2" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="0.6cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="3" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="1.2cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="4" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="1.8cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="5" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="2.4cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="6" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="3cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="7" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="3.6cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="8" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="4.2cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="9" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="4.8cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="10" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="5.4cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
		</text:list-style>
		<text:list-style style:name="L2">
			<text:list-level-style-bullet
				text:level="1" text:bullet-char="●">
				<style:list-level-properties />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="2" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="0.6cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="3" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="1.2cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="4" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="1.8cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="5" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="2.4cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="6" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="3cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="7" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="3.6cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="8" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="4.2cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="9" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="4.8cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="10" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="5.4cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
		</text:list-style>
		<text:list-style style:name="L3">
			<text:list-level-style-bullet
				text:level="1" text:bullet-char="•">
				<style:list-level-properties
					text:min-label-width="0.953cm" />
				<style:text-properties
					style:use-window-font-color="true" fo:font-size="100%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="2" text:bullet-char="•">
				<style:list-level-properties
					text:space-before="1.111cm" text:min-label-width="0.953cm" />
				<style:text-properties
					style:use-window-font-color="true" fo:font-size="100%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="3" text:bullet-char="•">
				<style:list-level-properties
					text:space-before="2.223cm" text:min-label-width="0.953cm" />
				<style:text-properties
					style:use-window-font-color="true" fo:font-size="100%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="4" text:bullet-char="•">
				<style:list-level-properties
					text:space-before="3.493cm" text:min-label-width="0.953cm" />
				<style:text-properties
					style:use-window-font-color="true" fo:font-size="100%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="5" text:bullet-char="•">
				<style:list-level-properties
					text:space-before="4.763cm" text:min-label-width="0.953cm" />
				<style:text-properties
					style:use-window-font-color="true" fo:font-size="100%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="6" text:bullet-char="•">
				<style:list-level-properties
					text:space-before="6.032cm" text:min-label-width="0.953cm" />
				<style:text-properties
					style:use-window-font-color="true" fo:font-size="100%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="7" text:bullet-char="•">
				<style:list-level-properties
					text:space-before="7.302cm" text:min-label-width="0.953cm" />
				<style:text-properties
					style:use-window-font-color="true" fo:font-size="100%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="8" text:bullet-char="•">
				<style:list-level-properties
					text:space-before="8.572cm" text:min-label-width="0.953cm" />
				<style:text-properties
					style:use-window-font-color="true" fo:font-size="100%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="9" text:bullet-char="•">
				<style:list-level-properties
					text:space-before="9.842cm" text:min-label-width="0.953cm" />
				<style:text-properties
					style:use-window-font-color="true" fo:font-size="100%" />
			</text:list-level-style-bullet>
			<text:list-level-style-bullet
				text:level="10" text:bullet-char="●">
				<style:list-level-properties
					text:space-before="5.4cm" text:min-label-width="0.6cm" />
				<style:text-properties fo:font-family="StarSymbol"
					style:use-window-font-color="true" fo:font-size="45%" />
			</text:list-level-style-bullet>
		</text:list-style>
	</office:automatic-styles>
	<office:body>
		<office:presentation>
		<!--  draw:name="Slide1"  -->
			<draw:page draw:style-name="dp1"
				draw:master-page-name="Master1-Layout1-title-Diapositive-de-titre"
				presentation:presentation-page-layout-name="AL1T33">
				<xsl:attribute name="draw:name"><xsl:value-of select="./@text"/></xsl:attribute>
				<draw:frame draw:name="Titre 1" presentation:style-name="pr1"
					draw:text-style-name="P2" draw:layer="layout" svg:width="21.59cm"
					svg:height="4.083cm" svg:x="1.905cm" svg:y="5.918cm"
					presentation:class="title">
					<draw:text-box>
						<text:p text:style-name="P1">
							<text:span text:style-name="T1"><xsl:value-of select="./@text"/>
							</text:span>
						</text:p>
					</draw:text-box>
				</draw:frame>
				<draw:frame draw:name="Sous-titre 2"
					presentation:style-name="pr2" draw:text-style-name="P2" draw:layer="layout"
					svg:width="17.78cm" svg:height="4.868cm" svg:x="3.81cm" svg:y="10.795cm"
					presentation:class="subtitle">
					<draw:text-box>
						<text:p text:style-name="P3">
							<text:span text:style-name="T2"><xsl:value-of select="./@subtitle"/>
							</text:span>
						</text:p>
					</draw:text-box>
				</draw:frame>
				<presentation:notes draw:style-name="dp2">
					<draw:page-thumbnail draw:style-name="gr1"
						draw:layer="layout" svg:width="14.848cm" svg:height="11.136cm"
						svg:x="3.075cm" svg:y="2.257cm" draw:page-number="1"
						presentation:class="page" />
					<draw:frame presentation:style-name="pr3" draw:layer="layout"
						svg:width="16.799cm" svg:height="13.114cm" svg:x="2.1cm" svg:y="14.107cm"
						presentation:class="notes" presentation:placeholder="true">
						<draw:text-box />
					</draw:frame>
				</presentation:notes>
			</draw:page>
			<xsl:for-each select="./o" >
			<!-- draw:name="Slide2"  -->
				<draw:page draw:style-name="dp1"
					draw:master-page-name="Master1-Layout2-obj-Titre-et-contenu"
					presentation:presentation-page-layout-name="AL2T18">
					<xsl:attribute name="draw:name"><xsl:value-of select="./@text" /></xsl:attribute>
					<draw:frame draw:name="Titre 1" presentation:style-name="pr4"
						draw:text-style-name="P2" draw:layer="layout" svg:width="22.86cm"
						svg:height="3.175cm" svg:x="1.27cm" svg:y="0.763cm"
						presentation:class="title">
						<draw:text-box>
							<text:p text:style-name="P1">
							<text:span text:style-name="T1"><xsl:value-of select="./@text" />
								</text:span>								
							</text:p>
						</draw:text-box>
					</draw:frame>
					<draw:frame draw:name="Espace réservé du contenu 2"
						presentation:style-name="pr5" draw:text-style-name="P2" draw:layer="layout"
						svg:width="22.86cm" svg:height="12.572cm" svg:x="1.27cm" svg:y="4.445cm"
						presentation:class="outline">
						<draw:text-box>
							<text:list text:style-name="L3">
							<xsl:for-each select="./o" >
								<text:list-item>
								<text:p text:style-name="P4">
										<text:span text:style-name="T2"><xsl:value-of select="./@text" /></text:span>
								</text:p>
										<text:list>
											<xsl:apply-templates select="./o" />
										</text:list>
								</text:list-item>
							</xsl:for-each>
							</text:list>
<!--							<text:list text:style-name="L3">
								<text:list-item>
									<text:p text:style-name="P4">
										<text:span text:style-name="T2">OGATE/TITRE-1.1
										</text:span>
									</text:p>
									<text:list>
										<text:list-item>
											<text:p text:style-name="P5">
												<text:span text:style-name="T3">OGATE/TITRE-1.2
												</text:span>
											</text:p>
										</text:list-item>
										<text:list-item>
											<text:p text:style-name="P5">
												<text:span text:style-name="T3">OGATE/TITRE-1.3
												</text:span>
											</text:p>
										</text:list-item>
									</text:list>
								</text:list-item>
								<text:list-item>
									<text:p text:style-name="P4">
										<text:span text:style-name="T2">OGATE/TITRE-1.4
										</text:span>
									</text:p>
								</text:list-item>
-->
						</draw:text-box>
					</draw:frame>
					<presentation:notes draw:style-name="dp2">
						<draw:page-thumbnail draw:style-name="gr1"
							draw:layer="layout" svg:width="14.848cm" svg:height="11.136cm"
							svg:x="3.075cm" svg:y="2.257cm" draw:page-number="2"
							presentation:class="page" />
						<draw:frame presentation:style-name="pr6" draw:layer="layout"
							svg:width="16.799cm" svg:height="13.365cm" svg:x="2.1cm" svg:y="14.107cm"
							presentation:class="notes" presentation:placeholder="true">
							<draw:text-box />
						</draw:frame>
					</presentation:notes>
				</draw:page>
			</xsl:for-each>
			<presentation:settings
				presentation:mouse-visible="false" />
		</office:presentation>
	</office:body>
</office:document-content>

</xsl:template>

<xsl:template match="o"
>
		<text:list-item>
		<text:p text:style-name="P5">
		<text:span text:style-name="T3"><xsl:value-of select="./@text" /></text:span>
		</text:p>
										<text:list>
											<xsl:apply-templates select="./o" />
										</text:list>
	</text:list-item>
</xsl:template>

</xsl:stylesheet>