var ctx = function() {
  var path = window.location.href;
  var pathName = window.location.pathname;
  var hostPath = path.substring(0, path.indexOf(pathName))
  var projectName = pathName.substring(0, pathName.substring(1).indexOf('/') + 1);
  return (hostPath + projectName);
}
