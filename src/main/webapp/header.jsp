<%@ page import="ucl.ac.uk.model.Model" %>
<%@ page import="ucl.ac.uk.model.ModelFactory" %>
<%@ page import="java.util.Set" %>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">Damla&#39;s Notes App</a>
        </div>

        <ul class="nav navbar-nav">
            <li><a href="index.jsp"><strong>Home</strong></a></li>

            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button">
                    Categories <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                <%
                    Model model = ModelFactory.getModel();
                    Set<String> categories = model.getAllCategories();
                    if (categories != null) {
                        for (String cat : categories) {
                %>
                            <li><a href="displayCategory?category=<%= cat %>"><%= cat %></a></li>
                <%
                        }
                    } else {
                %>
                        <li>No Categories</li>
                <%  }  %>
                </ul>
            </li>
        </ul>

    </div>
</nav>
<div class="clearBoth"></div>
