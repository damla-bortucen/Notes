<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ucl.ac.uk.classes.Note" %>
<%@ page import="ucl.ac.uk.classes.Image" %>
<%@ page import="java.util.Set" %>

<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Note</title>
  <jsp:include page="/meta.jsp"/>
</head>


<body>
<jsp:include page="/header.jsp"/>

<div class="container">
    <h2>Edit Note</h2>
    <%
        Note noteToEdit = (Note) request.getAttribute("noteToEdit");
        Set<String> categories = (Set<String>) request.getAttribute("categories");
        if (noteToEdit == null) {
    %>
        <p>Note not found</p>
    <%
        } else {
    %>
    <form method="POST" action="updateNote" enctype="multipart/form-data">

        <div class="form-group">
          <label for="newName">Note Name:</label>
          <input type="hidden" name="noteId" value="<%= noteToEdit.getId() %>">
          <input class="form-control" type="text" id="name" name="name" value="<%= noteToEdit.getName() %>">
        </div>

        <div class="form-group">
            <label for="content">Make Changes:</label>
            <textarea class="form-control" rows="5" id="content" name="content"><%= noteToEdit.getContent() %></textarea>
        </div>

        <h3>Current Image:</h3>
            <div>
                <%
                    Image image = noteToEdit.getImage();
                    if (image != null) {
                %>
                        <img src="<%= image.getImageName() %>">
                        <p>Currently selected image</p>
                        <label for="removeImage">Remove current image</label>
                        <input type="checkbox" id="removeImage" name="removeImage">
                <%
                    } else {
                %>
                        <p>No image uploaded for this note.</p>
                <%
                    }
                %>
            </div>
                <h3>Upload a New Image:</h3>
                <input type="file" name="image" class="form-control">
                <br>

        <div class="form-check">
            <label for="categories">Categories:</label>
            <%
                Set<String> noteCategories = noteToEdit.getCategories();

                for (String category : categories) {
                    if (noteCategories.contains(category)) {
            %>
                        <input class="form-check-input" type="checkbox" id="<%= category %>" name="categories" value="<%= category %>" checked>
                        <label class="form-check-label"><%= category %></label>
            <%      } else { %>
                        <input class="form-check-input" type="checkbox" id="<%= category %>" name="categories" value="<%= category %>">
                        <label class="form-check-label"><%= category %></label>
            <%      }
                }
            %>
        </div>

        <input class="form-control btn-success" type="submit" value="Save Changes">
    </form>
    <%
        }
    %>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>