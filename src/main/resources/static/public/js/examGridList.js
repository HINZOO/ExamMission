
        let indexNum=0;
        let isChk;
        const hiddenForm=document.forms["hiddenForm"];
        const modifyForm=document.forms["modifyForm"];
        const modifyModal=new bootstrap.Modal(document.getElementById("modifyModal"), {});
        const searchAndInsertForm=document.forms['searchAndInsertForm'];
        let loadCont=document.getElementById('loadCont');
		let show=true;
        
        let valArr= new Array();//배열

		//콤보박스
        modifyForm.addEventListener('submit', (e)=>{e.preventDefault();});
        const countryData = {
            korea: ["Seoul","Incheon","Daegu","Busan"],
            america: ["LA","NewYork"],
            japan: ["Tokyo","Fukuoka","Osaka"]
        };
        

    

        function nationAndCitySelect(selectElement) {
        	let formElement = selectElement.closest('form');
        	let nationSelect = formElement.querySelector('select[name="nation"]');
            //let nationSelect =formElement.querySelector('.form-select[name="nation"]');
            let chkValueInput=formElement.querySelector('.chkValue');
            let chkValue="";
           	let citySelect =formElement.querySelector('.cityList');
           
             let selectedCountry = nationSelect.value;
             let cityOptions = countryData[selectedCountry];
             citySelect.innerHTML = " "; // 기존 도시 옵션을 초기화
           
             if (cityOptions) {
             	let cityOption = document.createElement("label");
                 let checkbox = document.createElement("input");
                 checkbox.type = "checkbox";
                 checkbox.classList = "allCity";
                 checkbox.setAttribute("onchange","checkAll(this)");
                 cityOption.appendChild(checkbox);
                 cityOption.appendChild(document.createTextNode("전체"));
                 citySelect.appendChild(cityOption);
                 for (let i = 0; i < cityOptions.length; i++) {
                     let cityOption = document.createElement("label");
                     let checkbox = document.createElement("input");
                     checkbox.type = "checkbox";
                     checkbox.name = "city";
                     checkbox.value = cityOptions[i];
                     chkValue+=checkbox.value+" ";
                     //console.log(chkValue); 만약 chekbox.value가 Value.Arr의값과 같으면 되어있으면 checked.
                     if(valArr!=null){//만약 valArr값이 있으면
                    	 for(val of valArr){//valArr 값들중
                    		 if(val===checkbox.value){//checkbox.value와 같은 값을 찾아
                    	          checkbox.setAttribute("checked","");//checked
                    		 }
                    	 }
                     }                
                     cityOption.appendChild(checkbox);
                     cityOption.appendChild(document.createTextNode(cityOptions[i]));
                     citySelect.appendChild(cityOption);
                     
                 }
                 chkValueInput.value=chkValue;
                 chkValue="";
                 valArr=null;//리셋
             } 
        
        }
        

      
    //수정
        function loadModify(eId) {
            let url = `/examgrid/${eId}/detail.do`;
            $.ajax({
                url: url,
                method: 'GET',
                success: function(json) {
                	let formElement = document.forms['modifyForm'];
                	let nationSelect = formElement.querySelector('select[name="nation"]');
                	let chkValueInput = formElement.querySelector('.chkValue');
                	let citySelect = formElement.querySelector('.cityList');

                	for (let key in json) {
                        $("#modifyForm").find("input,select,radio").each(function() {
                            key = $(this).attr("name");
                            if (key === "gender") {
                                if ($(this).val() === json[key]) {
                                    $(this).prop("checked", true); // radio 버튼 체크 속성 주기
                                }
                            } else if (key === "nation") {
                                $(this).find("option").each(function() {
                                    if ($(this).val() === json[key]) {
                                        $(this).prop("selected", true); // option 선택 상태 설정                                     
                                    }
                                });                          
                            }else if(key==="city"){
                            	let values = json[key];
                            	//let valArr= new Array();
                            	valArr=values.split(",");
                            	for(let i=0; i<valArr.length;i++){
                            		console.log("체크된 배열"+valArr); 
                            		console.log("체크된 값"+$(this).val); 
                                    if ($(this).val() === valArr[i]) {
                                       $(this).prop("checked", true); // checkbox 요소 체크 속성 주기
                                    }
                            	}
                                        	
                            }else {
                                $(this).val(json[key]);
                            }
                            
                        });
                        
                	    (function() {
							show=false;
        	            	nationAndCitySelect(nationSelect);
        	            	showCheckBox(chkValueInput.parentNode);
        	            	})();
                    }
                    modifyModal.show();
              
                },
                error: function() {
                    // Handle error here
                }
            });
        }
		
      //도시 select창 보여지게 처리.

     function showCheckBox(selectElement){
        let formElement = selectElement.closest('form');
      	let selectBox= formElement.querySelector(`.selectBox`);
    	let cityList= formElement.querySelector('.cityList');
      	let selectedValuesElement= formElement.querySelector(` .selectedValues`);  
      	let selectedCheckboxes = formElement.querySelectorAll('input[name="city"]:checked');
    	let selectedValues = Array.from(selectedCheckboxes).map(function(checkbox) {
    	    	return checkbox.value;
	    });
    	
    	if(show) {
    		selectedValuesElement.textContent = selectedValues.join(', ');//기존정보유지..(근데 이전것을 가지고있는문제가있다.)
		 	cityList.style.display = "block";//도시리스트 창이보여짐		      
    	  	show = false;
	    } else {
			selectedValuesElement.textContent = selectedValues.join(', ')
	    	cityList.style.display = "none";//가려짐
	        show = true;
	    }
      }

     function checkAll(selectElement) {
	   	let formElement = selectElement.closest('form');
	   	let checkboxes = document.getElementsByName('city'); // 체크박스들을 선택
	   	let checkAllCheckbox = formElement.querySelector('.allCity'); // 전체 선택/해제 체크박스를 선택
	   	  for (var i = 0; i < checkboxes.length; i++) {
	   	    checkboxes[i].checked = checkAllCheckbox.checked; // 전체 선택/해제 체크박스의 상태에 따라 체크박스들을 선택 또는 해제
	   	  }
	}
    
    
	 async function modifyAction() {
         isChk=await formCheck(event,"modifyForm");
         console.log(isChk)
         if(isChk===true){
             modifyModal.hide();
             let url="/examgrid/handler.do";
             const data=new FormData(modifyForm);
             const resp=await fetch(url,{method:"PUT", body:data});
             if(resp.status===200){
                 const json=await resp.json();
                 if(json.handler>0){//"handler":"1"
                     await loadPageAjax()
                 }
            }else{
                alert("수정 실패");
             }
           }
       }

        async function register() {
            if (indexNum === 0) {
            	 await insertForm();             
            }
            if (indexNum > 0) {
                await saveData();
            }
            await loadPageAjax();
        }
		
        //삭제
        function deleteSelected() {
        	let indexNums = hiddenForm.querySelectorAll('input[name="indexNum"]:checked');
        	console.log(indexNums);
        	let selectedIds = [];
        for (let i = 0; i < indexNums.length; i++) {
            if (indexNums[i].checked) {
                selectedIds.push(indexNums[i].value);
            }
        }
        if (selectedIds.length > 0) {
        if (confirm("선택한 항목을 삭제하시겠습니까?")) {
            let form = document.createElement("form");
            form.setAttribute("method", "post");
            form.setAttribute("action", "./remove.do");

            for (let i = 0; i < selectedIds.length; i++) {
                let input = document.createElement("input");
                input.setAttribute("type", "hidden");
                input.setAttribute("name", "selectedIds");
                input.setAttribute("value", selectedIds[i]);
                form.appendChild(input);
            }
            document.body.appendChild(form);
            form.submit();
        }
        } else {
            alert("삭제할 항목을 선택해주세요.");
        }
    }


           function addRow() {
               indexNum += 1
               //아이디,이름,성별,국가,도시
               let newRow = loadCont.insertRow(0);
               let checkCell = newRow.insertCell(0);
               let uIdCell = newRow.insertCell(1);
               let nameCell = newRow.insertCell(2);
               let genderCell = newRow.insertCell(3);
               let nationCell = newRow.insertCell(4);
               let cityCell = newRow.insertCell(5);

               let checkInput = document.createElement("input")
               checkInput.type = "checkbox"
               checkCell.appendChild(checkInput);
               let uIdInput = document.createElement("input")
               uIdInput.type = "text"
               uIdInput.classList = "form-control"
               uIdInput.name = "uId"
               uIdCell.appendChild(uIdInput);

               let userNameInput = document.createElement("input")
               userNameInput.type = "text"
               userNameInput.classList = "form-control"
               userNameInput.name = "name"
               nameCell.appendChild(userNameInput);

               let genderCont = document.createElement("div");
               genderCont.classList="row";
               let genderMaleCont = document.createElement("div");
               genderMaleCont.classList="form-check col";
               let genderFemaleCont = document.createElement("div");
               genderFemaleCont.classList="form-check col";
               let maleRadio = document.createElement("input");
               maleRadio.type = "radio";
               maleRadio.name = "gender";
               maleRadio.value = "male";
               maleRadio.checked = true;
               maleRadio.classList ="form-check-input";
               let maleLabel = document.createElement("label")
               maleLabel.textContent = "남"
               maleLabel.classList = "form-check-label"

               let femaleRadio = document.createElement("input");
               femaleRadio.type = "radio";
               femaleRadio.name = "gender";
               femaleRadio.value = "female";
               femaleRadio.classList ="form-check-input"
               let femaleLabel = document.createElement("label")
               femaleLabel.textContent = "녀"
               femaleLabel.classList = "form-check-label"

               genderMaleCont.appendChild(maleRadio);
               genderMaleCont.appendChild(maleLabel);
               genderFemaleCont.appendChild(femaleRadio);
               genderFemaleCont.appendChild(femaleLabel);
               genderCont.appendChild(genderMaleCont);
               genderCont.appendChild(genderFemaleCont);
               genderCell.appendChild(genderCont);
			
               //

               let nationSelect = document.createElement("select");
               nationSelect.name = "nation";
               nationSelect.classList ="form-select";
               nationSelect.setAttribute("aria-label","nation");
               for (let country in countryData) {
                   let countryOption = document.createElement("option");
                   countryOption.value = country;
                   countryOption.textContent = country;
                   nationSelect.appendChild(countryOption);
               }
               nationSelect.setAttribute("onchange","nationAndCitySelect(this)")
               nationCell.appendChild(nationSelect);
       
               // multipleSelection 요소 생성
               let multipleSelection = document.createElement("div");
               multipleSelection.classList.add("multipleSelection");
               
               let selectBox = document.createElement("div");
               selectBox.classList.add("selectBox");
               selectBox.setAttribute("onclick","showCheckBox(this)")
        		multipleSelection.appendChild(selectBox);

               // 체크박스를 위한 select 요소 생성
               let select = document.createElement("select");
               select.classList.add("form-select", "chkValue");
               select.name = "chkValue";
               selectBox.appendChild(select);        

               // overSelect 요소 생성
               let overSelect = document.createElement("div");
               overSelect.classList.add("overSelect", "text-start");
               let selectedValues = document.createElement("span");
               selectedValues.classList.add("selectedValues");
               overSelect.appendChild(selectedValues);
               selectBox.appendChild(overSelect);

               // checkBoxes 요소 생성
               let checkBoxes = document.createElement("div");
               checkBoxes.id = "checkBoxes";
               checkBoxes.name = "checkBoxes";
               checkBoxes.classList.add("form-control", "text-start", "cityList");
               selectBox.appendChild(checkBoxes);

               cityCell.appendChild(multipleSelection);


           }

           async function saveData() {
               isChk = await formCheck(event, "hiddenForm");
               if (isChk === true) {
                   let url = `/examgrid/handler.do`
                   const data = new FormData(hiddenForm);
                   const resp = await fetch(url, {method: "POST", body: data});
                   if (resp.status === 200) {
                       const json = await resp.json();
                       if (json.handler > 0) {
                           //수정성공메세지
                       }
                   }
               }
           }



            //유효성검사 (입력폼)
            async function idCheck(uId) {
                const resp = await fetch(`/examgrid/${uId}/checkId.do`);
                const result = await resp.json();
                return result;

            }

         async function formCheck(e, formName) {
             e.preventDefault();
             let form = document.forms[`${formName}`];
             let uId = form.uId.value;
             let name = form.name.value;
             let genderChk = false;
             for (let i = 0; i < form.gender.length; i++) {
                 if (form.gender[i].checked === true) {//성별의 i가 체크되면, true
                     genderChk = true;//트루이면 체크된것.
                 }
             }
             let nation = form.nation.value;
             let city = [];
             form.querySelectorAll('input[name="city"]:checked').forEach((checkbox) => {
               city.push(checkbox.value);
             });

             let idCheckResult = await idCheck(uId);

             console.log("결과값출력" + uId + name + genderChk + nation + city)//확인
             if (idCheckResult > 0) {
                 alert("이미 존재하는 아이디입니다.")
                 isChk = false;
                 return isChk;

             } else {
                 if (uId === "" || name === "" || genderChk === false || nation === "" || city.length === 0) {
                     alert("값을 다 기입하세요.");
                     isChk = false;
                     return isChk;
                 } else {
                     isChk = true;
                     return isChk;
                 }
             }
        }
        //남녀 검색
        async function searchGender(event){
            await loadGenderPage(event.target.value)
        }

        async function loadGenderPage(gender) {
            let url = `/examgrid/${gender}/listBoard.do`
            const resp = await fetch(url);
            if (resp.status === 200) {
                let printText = await resp.text();
                loadCont.innerHTML = printText; // ajax 로 리스트 새로 불러오기
            }
        }
        
        async function loadPageAjax() {
            try {
                let url = "/examgrid/listBoard.do";
                const resp = await fetch(url, { method: "GET" });

                if (resp.status === 200) {
                    const data = await resp.json();
                    let ajaxTD = "";

                    data.forEach((item) => {
                    	ajaxTD += `<tr ondblclick="loadModify(${item.eId})" class="w-100 h-75 overflow-scroll" style="${item.gender === 'MALE' ? 'background:#FBFFDC' : 'background:#E6FFFD'}">`;
                        ajaxTD += "<td><input type='checkbox' name='indexNum' value='" + item.eId + "'></td>";
                        ajaxTD += "<td class='col'>" + item.uId + "</td>";
                        ajaxTD += "<td class='col'>" + item.name + "</td>";
                        ajaxTD += "<td class='col'>" + item.gender + "</td>";
                        ajaxTD += "<td class='col'>" + item.nation + "</td>";
                        ajaxTD += "<td class='col'>" + item.city + "</td></tr>";
                    });

                    $("#loadCont").html(ajaxTD); // jQuery를 사용하여 데이터를 삽입
                } else {
                    console.log("Error:", resp.status);
                }
            } catch (error) {
                console.log("Error:", error);
            }
        }
        
        
	
	    
		//저장
	    async function insertForm() {
	    	isChk = await formCheck(event, "searchAndInsertForm")
	    	console.log(isChk);
	    	if(isChk===true){
	    		 let formData = $('form[name="searchAndInsertForm"]').serializeArray(); // 기존 폼 데이터 가져오기
	    		 $.ajax({
		    	    url: "./register.do",
		    	    method: 'POST',
		    	    data: formData,
		    	    success: function(response) {
		    	    	 loadPageAjax();
		    	    },
		    	    error: function(xhr, status, error) {
		    	      // 서버로부터 응답을 받지 못했거나 오류가 발생했을 때 실행되는 코드입니다.
		    	     alert("실패")
		    	      // 오류 처리를 수행할 수 있습니다.
		    	    }
		    	  });
	    	}
	    	 
   	}
		

     

    


