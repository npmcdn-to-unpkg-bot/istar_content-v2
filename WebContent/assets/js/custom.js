	function isDuplicate (newEntry, columnName) {
		var tableIndex ;
		var flag = false;
		$('thead tr th').each(function(i, obj) {
			if(this.getAttribute("aria-label").includes(columnName)){
				tableIndex = i;
				return false; 
			} 
		});
		
		$('tbody tr').each(function(i, obj) {
			if($(this).find("td").get(tableIndex).innerHTML.trim().replace(/\s+/g,'').toUpperCase() == newEntry.trim().replace(/\s+/g,'').toUpperCase() ) {
				flag = true;
			}
		});
		
		return flag;
	}
	
	