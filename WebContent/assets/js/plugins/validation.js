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
	            		required: true
	            	},
	            	description:
	                {
	                    required: true
	                }
	            },
	                                
	            // Messages for form validation
	            messages:
	            {
	            	title:
	            	{
	            		required: 'Pleaes enter a valid image title'
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
	            		rangelength: [5, 50]
	            	}
	            },
	                                
	            // Messages for form validation
	            messages:
	            {
	            	folder_name:
	            	{
	            		required: 'Pleaes enter a valid folder name'
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
            		required: true
            	},
            	duration:
                {
                    required: true
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
            		required: 'Pleaes enter valid lesson title'
            	},
            	duration:
                {
                    required: 'Please enter valid duration',
                    min: 1
                },
            	tags:
            	{
            		required: 'Please enter at least one tag',
            		min: 1
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