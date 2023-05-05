const ws = new WebSocket('ws://localhost:8080/screen-capture');

ws.addEventListener('message', event => {
    // 이미지를 로드하기 위한 FileReader 객체 생성
    const reader = new FileReader();

    const imageData = event.data;

    // Blob 객체를 이용하여 이미지 로드
    reader.readAsDataURL(imageData);

    // FileReader의 onload 이벤트 핸들러에서 이미지를 생성하여 반환
    reader.onload = function() {
        const dataURL = reader.result;
        const img = new Image();
        img.src = dataURL;

        document.getElementById('image-container').innerHTML = '';
        document.getElementById('image-container').appendChild(img);
    };

    //imageElement.src = `data:image/png;base64,${btoa(String.fromCharCode(...new Uint8Array(imageData)))}`;

});
