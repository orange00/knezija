/**
 * Does the necessary preparations for the sync button to work, whether with
 * archive or with active records. <br> 
 * The result after this script runs is that the "syncAnnotation" click will trigger a FileChooser to 
 * be selected and then that file will be submitted via "syncForm". 
 * "syncForm" and 
 * Preconditions: <br>
 * Expects the synchronization form tag to have id "syncForm", the annotation which triggers it 
 * "syncAnnotation", and the file input tag "file-input".
 * Expects a p tag with id "isArchive" with some text if the current records are
 * archive records, or without any text if the current records are active
 * records.<br>
 * Expects two js variables which have "syncForm" action attribute values as the variable value: <br>
 * archiveSyncAction, activeRecordsSyncAction
 */
var isArchiveTag = $("#isArchive");
var isArchiveText = isArchiveTag.text();
var isArchive = isArchiveText != "" && isArchiveText != null;
if(isArchiveText=="false") {
	isArchive=false;
} else if(isArchiveText=="true") {
	isArchive=true;
}
if (isArchive) {
	$("#syncForm").attr("action", archiveSyncAction);
} else {
	$("#syncForm").attr("action",
			activeRecordsSyncAction);
}

$("#syncAnnotation").click(function(e) {
	e.preventDefault();
	$("#file-input").click();
});

$('#file-input').change(function() {
	$('#syncForm').submit();
});