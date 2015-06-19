<%-- 
    Document   : index
    Created on : 22 avr. 2013, 14:36:12
    Author     : clem
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  <!--Il faut bien utiliser la vesion 1.1 d ela jstl l'autre ne permet pas d'utiliser les EL-->
<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<!--Inclusion du menu haut-->
<c:import url="/WEB-INF/headerjsp.jsp" />


<div id="header-wrapper">
    <div id="header">
        <div id="logo">
            <c:import url="/WEB-INF/inc/titre.jsp" />
        </div></div>

</div>

<div id="content">
    
    <c:choose>
        
        <c:when test="${action=='read'}">
            <div class="post">
                <h2><a href="${item.link}">${item.titre}</a></h2>
                <p>
                    Provenance : 
                <ul>

                    <c:forEach items="${item.listFlux}" var="flux">
                        <li><a href="${flux.readURL}">${flux}</a></li>
                        </c:forEach>

                </ul>
                </p>
                <p>Date pub : <fmt:formatDate value="${item.datePub}" pattern="dd/MM/yyyy HH:mm:ss"/></p>
                <p>Date récup <fmt:formatDate value="${item.dateRecup}" pattern="dd/MM/yyyy HH:mm:ss"/></p>
                <p>Guid : ${item.guid}</p>
                <p>contenu : ${item.contenu}</p>

                <p>Description ${item.description}</p>
                <p></p>

                <hr />

                <div id="divDonneeBrutes"></div>
                <script src="${rootpath}AjaxItemDynGrid.js"></script>


            </div>
        </c:when>



        <c:when test="${action=='recherche'}">

            <!--            <link rel="stylesheet" href="jquery-ui.css" />
                        <script src="jquery-ui.js"></script>-->

            <script>
//                $(function() {
//                    $(".datepicker").datepicker({dateFormat: "yy-mm-dd"});
//                });

                $(function() {
                    $("#date1").datepicker({
                        defaultDate: "+1w",
                        dateFormat: "yy-mm-dd",
                        changeMonth: true,
                        numberOfMonths: 3,
                        onClose: function(selectedDate) {
                            $("#date2").datepicker("option", "minDate", selectedDate);
                        }
                    });
                    $("#date2").datepicker({
                        defaultDate: "+1w",
                        dateFormat: "yy-mm-dd",
                        changeMonth: true,
                        numberOfMonths: 3,
                        onClose: function(selectedDate) {
                            $("#date1").datepicker("option", "maxDate", selectedDate);
                        }
                    });
                });


            </script>



            <div class="post">
                <h1>Liste des items</h1>
                <div>

                    <form method="GET" id="pagina" action="${rootpath}item/list">

                        <input type="hidden" id="firstResult" value="0"/>

                        <br />
                        <fieldset>
                            <legend>Affiner la recherche</legend>

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
                                        <label>Jounal :</label>
                                        <select id="journalSelection" style="width: 10em">
                                            <option value="null">Journal : </option>
                                            <option id="tous">tous</option>
                                            <c:forEach items="${listJournaux}" var="j">
                                                <option value="${j.ID}">${j}</option>
                                            </c:forEach>
                                        </select><br />
                                        
                                        <label>Type : </label>
                                        
                                        
                                        <select id="typeSelection" style="width: 10em">
                                            <option value="null"></option>
                                            <c:forEach items="${listType}" var="ty">
                                                <option value="${ty.ID}">${ty}</option>
                                            </c:forEach>
                                            
                                        </select>
                                        
                                        
                                    </td>
                                    <td>

                                        <ul id="fluxSelection" name="oldid-flux" style="width: 15em" class="connectedSortable">
                                            <c:forEach items="${listflux}" var="fl">
                                                <li class="boxelement" value="${fl.ID}">${fl}</li>                                
                                                </c:forEach>
                                            </li>
                                        </ul>

                                    </td>
                                    <td>

                                        <button type="button" id="btAddAll">=></button><br />
                                        <button type="button" id="btRemAll"><=</button>
                                    </td>
                                    <td><ul style="min-width: 10em; width: 15em" name="fluxSelection2" id="fluxSelection2" class="connectedSortable"></ul></td>
                                </tr>

                            </table>


                            <script src="dynListJournauxFLux.js"></script>   <!--Le Script permettant de gérer la sélection des flux -->
                            <script src="AjaxItemDynGrid.js"></script> <!--Le script permettant de gérer la Grid d'affichage des Item-->
                            <br />

                            <!--                            <label>Ordonner par : </label>
                                                        <select id="order" name="order">
                                                            <option value=""></option>
                                                            <option value="dateRecup" <c:if test="${param.order=='dateRecup'}">selected="true"</c:if>>Date de récupération</option>
                                                            <option value="datePub" <c:if test="${param.order=='datePub'}"> selected="true"</c:if>>Date de publication</option>
                                                            <option value="listFlux" <c:if test="${param.order=='listFlux'}"> selected="true"</c:if>>Flux</option>
                                                            </select>-->


                                <!--                                <label for="desc">Décroissant</label>
                                                                <input type="checkbox" name="desc" value="true" <c:if test="${param.desc=='true'}"> checked="true"</c:if>/>-->


                                <label for="date1">Date début : </label>
                                <input type="text" name="date1" class="datepicker" id="date1"/>
                                <label for="date2">Date fin : </label>
                                <input type="text" name="date2" class="datepicker" id="date2"/>

                                <input type="button" value="Rechercher" id="afin">
                                <input type="button" value="Reset" id="reset"/>

                                <!--                                <select name="vue" id="vue" onchange="subExport();">
                                                                    <option value="html">Export de la sélection</option>
                                                                    <option value="csv">CSV</option>
                                                                    <option value="csvexpert">CSV Expert</option>
                                                                    <option value="xls">XLS</option>
                                                                </select>-->
                                <!--                                <button type="submit"  formaction="Export" formtarget="_blank">Exporter</button>-->
                                <script>
                function subExport() {
                    if ($('#vue').val() == 'csv' || $('#vue').val() == 'csvexpert' || $('#vue').val() == 'xls') {
                        var old = $('#order').val();
                        $('#order').val('listFlux');
                        $('#pagina').submit();
                        $('#order').val(old);
                    }
                }
                                </script>

                            </fieldset>

                        </form>


                        <script src="${rootpath}ress/jqgrid/js/i18n/grid.locale-fr.js" type="text/javascript"></script>
                    <script src="${rootpath}ress/jqgrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>
                    <!--<script src="${rootpath}ress/jqgrid/plugins/grid.addons.js" type="text/javascript"></script>-->

                    <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.7.1.custom.css" />
                    <link rel="stylesheet" type="text/css" media="screen" href="${rootpath}ress/jqgrid/css/ui.jqgrid.css" />
                    <link rel="stylesheet" type="text/css" media="screen" href="${rootpath}ress/jquery-ui-1.10.3.custom/css/base/jquery-ui.css" />

                    <table id="list" width="600"><tr><td></td></tr></table> 
                    <div id="pager"></div> 

                    <div id="mysearch"></div>



                </div>

                <div>
                    <ul id="resudiv">

                    </ul>

                </div>

                <!--                    <ul>    MAINTENANT GERRÉE EN aAJAX-->  
                <%--<c:forEach items="${listItem}" var="ite">--%>
                <!--<li><p>-->
                        <!--<a href="item?action=read&id=${ite.ID}">${ite.titre}</a>-->
                <%--<c:forEach items="${ite.listFlux}" var="fl">--%>
                    <!--"${fl}"--> 
                <%--</c:forEach><fmt:formatDate value="${ite.dateRecup}" pattern="dd/MM/yyyy hh:mm:ss"/>--%>
                <!--</p>-->
                <!--<p>${ite.description}</p>-->
                <!--</li>-->
                <%--</c:forEach>--%>
                <!--</ul>-->


            </div>
        </c:when>

    </c:choose>








    <!--
    





</div>



    <!--<script>
    
    
                                    $(document).ready(function() {
    
                                        $('.pagination').jqPagination({
                                            link_string: 'item?page={page_number}',
                                            max_page: ${maxPage},
                                            current_page: ${pageCourante},
                                            paged: function(page) {
                                                $('.log').prepend('<li>Requested page ' + page + '</li>');
                                                document.location.href = "item?page=" + page;
                                            }
                                        });
    
                                    });
    
    
    </script>-->
</div>

<c:import url="/WEB-INF/footerjsp.jsp" />