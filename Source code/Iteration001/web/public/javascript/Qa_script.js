
function toggleBox(id) {
    if (document.getElementById(id).style.display == 'none'){
        document.getElementById(id).style.display = 'block';
    } else {
        document.getElementById(id).style.display = 'none';
    }
}

function closeBox(id) {
    document.getElementById(id).style.display = 'none';
}