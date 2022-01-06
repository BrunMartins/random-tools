function caeser(message, key, rounds = 1, decrypt = false) {
    if (key.includes('rand')) {
        key = Math.ceil(Math.random() * (Math.floor(parseInt(key.replace('rand', ''))) - 2) + 2);
    }
    
    if (isNaN(key * 1)) {
        return "The key argument must be numeric";
    } else if (isNaN(rounds * 1)) {
        return "The rounds argument must be numeric";
    } else if (typeof decrypt !== 'boolean') {
        return "The decrypt argument must be a boolean";
    }

    key = decrypt ? key * -1 : key;
    const alphabet = "abcdefghijklmnopqrstuvwxyz";
    const messageParts = Array.from(message);
    const nextRounds = rounds - 1;

    let encryptedMessage = messageParts.map(char => {
        let index = alphabet.indexOf(char.toLowerCase()) + key;
        index = index > (alphabet.length - 1) ? index - alphabet.length : index;
        return char === " " ? char 
                : (!isNaN(char * 1) ? (parseInt(char) + key).toString() 
                : (char === char.toUpperCase() ? alphabet[index].toUpperCase() 
                : alphabet[index]));
    }).join('');

    return nextRounds > 0 ? caeser(encryptedMessage, key, nextRounds, decrypt) : encryptedMessage;
}
