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
	            		required: 'Please enter a valid image title',
	            		rangelength: [5, 50]
	            	},
	            	description:
	                {
	                    required: 'Please enter the task description(Tags and target folders)'
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
	            		required: 'Please enter a valid folder name'
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
                    min: 0
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
            		required: 'Please enter valid lesson title'
            	},
            	duration:
                {
                    required: 'Please enter valid duration',
                },
            	tags:
            	{
            		required: 'Please enter at least one tag',
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