<%-- 
    Document   : incidentJsp
    Created on : 22 mai 2013, 14:53:49
    Author     : clem
--%>

<%@page import="rssagregator.servlet.IncidentsSrvl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  <!--Il faut bien utiliser la vesion 1.1 d ela jstl l'autre ne permet pas d'utiliser les EL-->
<c:import url="/WEB-INF/headerjsp.jsp" />



<div id="header-wrapper">
    <div id="header">
        <div id="logo">
            <c:import url="/WEB-INF/inc/titre.jsp" />
        </div></div>
</div>

<div id="content"> 
    <div class="post">

        <c:choose>
            <c:when test="${not empty redirmap}">
                <c:import url="/WEB-INF/redirJspJavascriptPart.jsp" />
            </c:when>


            <c:when test="${empty redirmap}">


                <c:choose>
                    <c:when test="${action=='recherche'}">
                        <h2>Liste des incidents</h2>

                        <form method="POST" id="pagina" action="${rootpath}incidents/list">
                            <input type="hidden" name="action" value="list"/>
                            <input type="hidden" name="vue" value="jsondesc" />

                            <fieldset>
                                <legend>Pages : </legend>
                                <div>
                                    <span id="btPaginDiv"></span>
                                </div>


                                <input type="hidden" name="firstResult" id="firstResult" value="0"/> 


                                <label>Type D'incident :</label>
                                <label>Tous type : </label><input type="radio" name="type" value="AbstrIncident" /><br />
                                <label title="tous ce qui est relatif a la collecte">Tous type relatif a la collecte </label>:<input type="radio" name="type" value="CollecteIncident" checked="checked" id="type" /><br />
                                <label title="Incident généré lorsque le flux est indisponible">Incident de récupération </label>:<input type="radio" name="type" value="RecupIncident" checked="checked" id="type" /><br />
                                <label title="Anomalie générée lorsqu'on constate une absence d'item anormale">Anomalie de Collecte </label>:<input type="radio" name="type" value="AnomalieCollecte" checked="checked" id="type" /><br />
                                <!--<label>Synchronisation </label>: <input type="radio" name="type" value="SynchroIncident"><br />-->
                                <label>Serveur :</label> <input type="radio" name="type" value="ServerIncident"><br />
                                <!--<label>Mail :</label> <input type="radio" name="type" value="MailIncident"/>-->


                                <hr />
                                <!--<br />-->
                                <!--                                <label>Entité par page</label>
                                                                <select id="itPrPage" name="itPrPage" onChange="this.form.submit();"> 
                                <c:forEach var="i" begin="25" end="150" step="25">
                                    <option value="${i}" <c:if test="${itPrPage==i}"> selected="selected"</c:if>>${i}</option>
                                </c:forEach>
                            </select><br />-->
                                <label>Voir : </label>
                                <input type="radio" id="clos" name="clos" value="true"<c:if test="${clos}"> checked="checked"</c:if> onclick="$('afin').click();">Incident clos
                                <input type="radio" name="clos" value="false"<c:if test="${!clos}"> checked="checked"</c:if> onclick="$('afin').click();">Incident non clos


                                    <br />
                                    <!--<button value="0" name="limiterFlux" id="limiterFlux" type="button">Limiter aux flux</button>-->
                                    <!--                                    <div id="divLimiterFluxContener">
                                                                            <div id="divLimiterFlux">
                                                                                <label>Flux lie : </label>
                                                                                <table>
                                                                                    <caption>Flux de provenance</caption>
                                                                                    <tr>
                                                                                        <th>Journaux</th>
                                                                                        <th>Flux disponibles</th>
                                                                                        <th></th>
                                                                                        <th>Flux sélectionnés</th>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>
                                                                                            <select id="journalSelection" style="width: 300px">
                                                                                                <option value="null">Journal : </option>
                                                                                                <option id="tous">tous</option>
                                <c:forEach items="${listJournaux}" var="j">
                                    <option value="${j.ID}">${j.nom}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select id="fluxSelection" name="oldid-flux" style="min-width: 300px; width: 400px" multiple="true">
                                <option value="all">Tous</option>
                                <c:forEach items="${listflux}" var="fl">
                                    <option value="${fl.ID}">${fl}</option>                                
                                </c:forEach>
                            </select>

                        </td>
                        <td>

                            <button type="button" onclick="selectflux();"></button><br />
                            <button type="button" onclick="supp();"><--</button>
                        </td>
                        <td><select multiple="true" style="max-width: 300px; width: 300px" name="fluxSelection2" id="fluxSelection2">
                                <c:forEach items="${fluxsel}" var="f">
                                    <option value="${f.ID}">${f}</option>
                                </c:forEach>

                            </select></td>
                    </tr>

                </table>
            </div>
        </div>-->-->

                                <input type="hidden" name="requestOnStart" id="requestOnStart" value="${requestOnStart}"/>
                                <script src="${rootpath}AjaxIncidDynGrid.js"></script>
                                <script src="${rootpath}dynListJournauxFLux.js"></script>


                                <button type="button" id="afin" >Affiner</button>
                            </fieldset>

                        </form>
                        <div id="disabledElement"></div>


                        <ul id="resudiv">

                        </ul>


                        <table id="list" width="600"></table> 
                        <div id="pager"></div> 


                        <script src="${rootpath}ress/jqgrid/js/i18n/grid.locale-fr.js" type="text/javascript"></script>
                        <script src="${rootpath}ress/jqgrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>

                        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.7.1.custom.css" />
                        <link rel="stylesheet" type="text/css" media="screen" href="${rootpath}ress/jqgrid/css/ui.jqgrid.css" />
                        <link rel="stylesheet" type="text/css" media="screen" href="${rootpath}ress/jquery-ui-1.10.3.custom/css/base/jquery-ui.css" />

                        <script type="text/javascript">

                        </script>




                        <c:if test="${admin=='true'}">
                            <link rel="alternate" href="${rootpath}incidents/rssBakend" title="Le Monde.fr : A la une" type="application/rss+xml">
                            <p><a href="${rootpath}incidents/rssBakend">Flux RSS des incidents</a></p>
                        </c:if>

                    </c:when>


                    <c:when test="${action=='mod'}">

                        <h2>Description de l'incident</h2>
                        <c:if test="${bean['class'].simpleName=='FluxIncident'}"><p>Flux Impacté : <a href="flux/mod?id=${bean.fluxLie.ID}">${bean.fluxLie}</a></p></c:if>
                        <p>Date début : <fmt:formatDate value="${bean.dateDebut}" pattern="dd/MM/yyyy hh:mm:ss"/></p>
                        <p>Date fin : <fmt:formatDate value="${bean.dateFin}" pattern="dd/MM/yyyy hh:mm:ss"/></p>
                        <p>Nombre de répétition dans la période : ${bean.nombreTentativeEnEchec}</p>
                        <p>Message d'erreur : ${bean.messageEreur}</p>
                        <p>Log JAVA de l'erreur : ${bean.logErreur}</p>

                        <form method="POST" action="${rootpath}incidents/mod?id=${bean.ID}">
                            <c:if test="${empty bean.dateFin}">
                                <label>Clore l'incident : </label><input type="checkbox" name="dateFin" /><br />
                            </c:if>


                            <input type="hidden" name="type" value="${bean['class'].simpleName}"/>
                            <textarea name="noteIndicent" id="noteIndicent" cols="80" rows="30">${bean.noteIndicent}</textarea><br />
                            <input type="submit">
                        </form>
                        ${bean['class'].simpleName}
                    </c:when>
                    <c:when test="${action=='read'}">

                        <c:if test="${admin == 'true'}"><p><a href="${rootpath}incidents/mod?id=${bean.ID}&type=${bean['class'].simpleName}">EDITER</a></p></c:if>



                        <c:if test="${bean['class'].simpleName=='CollecteIncident'}">
                            <p><strong>Flux impacté : </strong><a href="${rootpath}flux/read?id=${bean.fluxLie.ID}">${bean.fluxLie}</a></p>
                            </c:if>


                        <c:if test="${bean['class'].simpleName=='ServerIncident'}">

                        </c:if>

                        <c:if test="${bean['class'].simpleName=='NotificationAjoutFlux'}">
                            <p>Des flux ont automatiquement été ajouté pour le journal : <a href="${bean.journal.readURL}"> ${bean.journal}</a></p>
                            <h2>Flux ajouté :</h2> 
                            <ul>
                                
                                <c:forEach items="${bean.fluxAjoute}" var="fl">
                                    <li><a href="${fl.readURL}">${fl}</a></li>
                                    </c:forEach>
                            </ul>
                        </c:if>
                            

                        <c:if test="${bean['class'].simpleName=='AnomalieCollecte'}">
                            
                            <p><strong>Flux concerné : </strong>${bean.fluxLie}</p>
                            <h3>Durée de l'anomalie</h3>
                            <ul>
                            <c:forEach items="${bean.periodeAnormale}" var="p">
                                <li><fmt:formatDate value="${p.dateAnomalie}" pattern="dd/MM/yyyy"/> : ${p.nbrItemCollecte} items collectés</li>
                            </c:forEach>
                                </ul>
                        </c:if>

                        <p><strong>Date début :</strong> <fmt:formatDate value="${bean.dateDebut}" pattern="dd/MM/yyyy hh:mm:ss"/></p>
                        <p><strong>Date fin :</strong> <fmt:formatDate value="${bean.dateFin}" pattern="dd/MM/yyyy hh:mm:ss"/></p>
                        <p><strong>Nombre de répétition dans la période :</strong> ${bean.nombreTentativeEnEchec}</p>
                        <p><strong>Message d'erreur :</strong> ${bean.messageEreur}</p>
                        <p><strong>Log JAVA de l'erreur :</strong> ${bean.logErreur}</p>
                        <p><strong>Commentaire des administrateurs : </strong> ${bean.noteIndicent}</p>
                    </c:when>


                </c:choose>
            </c:when>
        </c:choose>

    </div>
</div>



<c:import url="/WEB-INF/footerjsp.jsp" />