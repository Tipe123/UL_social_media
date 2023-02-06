const CLOUDINARY_URL = "https://api.cloudinary.com/v1_1/room-locator/upload"
        const CLOUDINARY_UPLOAD_PRESET = "mkopuho4"

        var signupForm = document.getElementById("signup-form")
        var imgPreview = document.getElementById("img-preview")
        var fileUpload = document.getElementById("file-upload")
        var imageValue =  document.getElementById("image-value")
        var userValue =  document.getElementById("user-id")

        console.log([[${user.getId()}]])
       console.log(imgPreview)

        fileUpload.addEventListener("change",(event)=>{

        var file = event.target.files[0];
        var formData = new FormData();
        formData.append('file',file);
        formData.append('upload_preset',CLOUDINARY_UPLOAD_PRESET)

        axios({
        url:CLOUDINARY_URL,
        method:'POST',
        headers:{
            'Content-Type':'application/x-www-form-urlencoded'
        },
        data:formData
        }).then((response)=>{
            imgPreview.src = response.data.url
            handleChange(response.data.url)
        }).catch((error)=>{
            console.log(error)
        })
    });

       handleChange = (value) =>{
        imageValue.value = value;
        userValue.value = [[${user.getId()}]]
        //Create an element to submit

        var element = document.createElement('button')
        element.innerHTML = "submit"

        signupForm.appendChild(element)

        }

