<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
<div class="container">
<h1>Contact</h1>
<p>Contactez-nous pour toute question, suggestion, témoignage, etc.</p>
<br>
<form role="form" method="POST" action='<%=response.encodeURL("contactsubmit")%>' style="margin-left:15%; width:70%;">
  <div class="form-group">
    <input type="email" class="form-control" id="email" name="sender" placeholder="Votre e-mail" required>
  </div>
  <div class="form-group">
    <input type="text" class="form-control" id="subject" name="subject" placeholder="Sujet" required>
  </div>
  <div class="form-group">
    <textarea id="body" placeholder="Votre message" name="content" class="form-control" rows="5" required></textarea>
  </div>
  <button type="submit" class="btn btn-primary">Envoyer</button>
</form>
</div>
</body>