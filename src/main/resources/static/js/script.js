const search = ()=> {
	
	//let query = $('#searchinput').val();
	let query = document.getElementById('searchinput');
	if(query=='')
	{
		$("search-result").hide();
	}
	else
	{
		
		let url =`http://localhost:8080/search/${query}`;
		
		fetch(url).then((response)=>{
			return response.text();
		}).then((data)=>{
			console.log(data);	
			let text =`<div class='list-group'>`;
			
			data.forEach((con)=>{
				text+=`<a href='#' class='list-group-iteam list-group-action' >${con.name}</a>`;
			})
			
			///find/${con.id}
			text+=`</div>`;
//			$("search-result").html(text);
//			$("search-result").show();
			
		});
		
		console.log(query);	
		$("search-result").show();
		
	}
	
}