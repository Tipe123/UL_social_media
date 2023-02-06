const inputs = document.querySelectorAll(".input");

const container = document.querySelector(".container");
const signUpBtn = document.querySelector(".green-bg button");

function addcl(){
	let parent = this.parentNode.parentNode;
	parent.classList.add("focus");
}

function remcl(){
	let parent = this.parentNode.parentNode;
	if(this.value == ""){
		parent.classList.remove("focus");
	}
}

inputs.forEach(input => {
	input.addEventListener("focus", addcl);
	input.addEventListener("blur", remcl);
})

signUpBtn.addEventListener("click", () => {
  container.classList.toggle("change");
});


