window.onload = function () {
	// Add TaskボタンへTODO追加処理設定
	document.querySelector(".todo-button").addEventListener('click', addTask);
	// 更新アイコンへTODO更新処理設定
	document.querySelectorAll(".update-icon").forEach((doc) => {
		doc.addEventListener('click', updTask);
	});
	// 実施・未実施へTODO更新処理設定
	document.querySelectorAll(".todo-check").forEach((doc) => {
		doc.addEventListener('change', updTask);
	});
	// 削除アイコンへTODO削除処理設定
	document.querySelectorAll(".trash-icon").forEach((doc) => {
		doc.addEventListener('click', delTask);
	});
}

/**
 * Formへ送信用の値を設定する
 */
function setForm(sbt, pDoc) {
	sbt.querySelector('#id').value = pDoc.querySelector('#todo_id').value || -1;
	sbt.querySelector('#title').value = pDoc.querySelector('#todo_title').value;
	sbt.querySelector('#done_flg').value = pDoc.querySelector('#todo_done_flg').checked ? 1 : 0;
	sbt.querySelector('#time_limit').value = pDoc.querySelector('#todo_limit').value;
}

/**
 * TODO追加
 */
function addTask(e) {
	e.preventDefault();
	console.log('addTask', e);
	let sbt = document.querySelector("#todo");
	sbt.setAttribute("action", "/add");
	sbt.querySelector('#id').value = -1;
	sbt.submit();
}

/**
 * TODO更新
 */
function updTask(e) {
	e.preventDefault();
	console.log('updTask', e);
	let sbt = document.querySelector("#todo");
	sbt.setAttribute("action", "/change");
	setForm(sbt, e.currentTarget.parentElement);
	sbt.submit();
}

/**
 * TODO削除
 */
function delTask(e) {
	e.preventDefault();
	console.log('delTask', e);
	let sbt = document.querySelector("#todo");
	sbt.setAttribute("action", "/delete");
	setForm(sbt, e.currentTarget.parentElement);
	sbt.submit();
}