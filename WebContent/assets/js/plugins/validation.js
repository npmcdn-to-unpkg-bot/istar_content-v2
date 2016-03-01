var Validation = function () {

    return {
        
        //Validation
    	createMediaTaskValidation: function () {
	        $("#sky-form1").validate({                   
	            // Rules for form validation
	            rules:
	            {
	            	title:
	            	{
	            		required: true,
	            		rangelength: [5, 50]
	            	},
	            	description:
	                {
	                    required: true,
	            		rangelength: [5, 150]
	                }
	            },
	                                
	            // Messages for form validation
	            messages:
	            {
	            	title:
	            	{
	            		required: 'Please enter media title',
	            		rangelength: 'Task title should be between 5 to 50 characters length'
	            	},
	            	description:
	                {
	                    required: 'Please enter the task description(Tags and target folders)',
	            		rangelength: 'Task description should be between 5 to 150 characters length'
	                }
	            },                  
	            
	            // Do not change code below
	            errorPlacement: function(error, element)
	            {
	                error.insertAfter(element.parent());
	            }
	        });
        },

        createFolderValidation: function () {
	        $("#sky-form1").validate({                   
	            // Rules for form validation
	            rules:
	            {
	            	folder_name:
	            	{
	            		required: true,
	            		rangelength: [3, 50]
	            	}
	            },
	                                
	            // Messages for form validation
	            messages:
	            {
	            	folder_name:
	            	{
	            		required: 'Please enter folder name',
	            		rangelength: 'Folder name should be between 3 to 50 characters long'
	            	}
	            },                  
	            
	            // Do not change code below
	            errorPlacement: function(error, element)
	            {
	                error.insertAfter(element.parent());
	            }
	        });
        },

        assignCreativeValidation: function () {
	        $("#sky-form1").validate({                   
	            // Rules for form validation
	            rules:
	            {
	            	folder_name:
	            	{
	            		required: true,
	            		rangelength: [3, 50]
	            	}
	            },
	                                
	            // Messages for form validation
	            messages:
	            {
	            	folder_name:
	            	{
	            		required: 'Please enter folder name',
	            		rangelength: 'Folder name should be between 3 to 50 characters long'
	            	}
	            },                  
	            
	            // Do not change code below
	            errorPlacement: function(error, element)
	            {
	                error.insertAfter(element.parent());
	            }
	        });
        },
        
        lessonValidation: function () {
        	$("#sky-form4").validate({                   
            // Rules for form validation
        		rules:
        		{
        			title:
        			{
        				required: true,
        				rangelength: [5, 50]
        			},
        			duration:
        			{
        				required: true,
        				range: [1,120]
        			},
        			tags:
        			{
        				required: true
        			}
        		},
                                
        		// Messages for form validation
	            messages:
	            {
	            	title:
	            	{
	            		required: 'Please enter valid lesson title',
	            		rangelength: 'Title should be between 5 to 50 characters long'
	            	},
	            	duration:
	                {
	                    required: 'Please enter valid duration in minutes',
	                    range: 'Duration shoud be between 1 minute to 120 minutes'
	                },
	            	tags:
	            	{
	            		required: 'Please enter at least one tag'
	            	}
	            },                  
	            
	            // Do not change code below
	            errorPlacement: function(error, element)
	            {
	                error.insertAfter(element.parent());
	            }
	        });
        }
    };
}();