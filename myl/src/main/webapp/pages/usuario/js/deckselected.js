$(document).ready(function() {
	var selection=$("#deck").val();
	$("input[name=deckpred][value=" + selection + "]").prop('checked', true);
//	$("#result").val($("#h"+selection).val());
	$("#result").text($("#h"+selection).val());
	
});


function setSelection(selection)
{
	$("#result").text($("#h"+selection).val());
//	$("#result").val($("#h"+selection).val());
	
$.ajax({
	url : $("#context").val() + "/usuario!setDeckPredeterminado?deckId=" + selection,
	type : "POST",
	error : function() {
		alert('Error');
	},
	success : function(data) {		
	}
});

}
