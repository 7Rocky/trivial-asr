const loadingData = () => {
  document.querySelectorAll('h4 .question')
    .forEach(h4 => {
      h4.innerHTML = '<div class="spinner-border" role="status"><span class="visually-hidden">Loading...</span></div>'
    })

  document.querySelectorAll('label')
    .forEach(label => {
      label.innerHTML = '<div class="spinner-grow" role="status"><span class="visually-hidden">Loading...</span></div>'
    })
}

const putAudio = button => {
  const parent = button.parentElement
  const number = parent.querySelector('.question-number').textContent
  const audio = new Audio(`audio?q=${number - 1}`)
  audio.controls = true
  audio.autoplay = true

  parent.querySelector('h4').after(audio)
  parent.removeChild(parent.querySelector('button'))
}

const decodeHtml = html => {
  let areaElement = document.createElement("textarea");
  areaElement.innerHTML = html;

  return areaElement.value;
}

const selectLanguage = language => {
  const button = document.getElementById('dropdown-languages')
  const ul = button.nextElementSibling

  button.textContent = language

  ul.querySelectorAll('a').forEach(a => {
    if (a.textContent === language) {
      a.classList.replace('text-white', 'text-dark')
      a.classList.replace('bg-dark', 'bg-info')
    } else {
      a.classList.replace('text-dark', 'text-white')
      a.classList.replace('bg-info', 'bg-dark')
    }
  })
}

const loadQuiz = async a => {
  selectLanguage(a.textContent)

  const button = document.getElementById('dropdown-languages')
  button.disabled = true

  animation = true
  loadingData()

  const language = a.id
  const res = await fetch(`questions?language=${language}`)
  const questions = await res.json()
  console.log(questions)

  document.querySelectorAll('.card-body')
    .forEach((card, numQuestion) => {
      card.querySelectorAll('h4')
        .forEach(h4 => {
          h4.lastElementChild.textContent = decodeHtml(questions[numQuestion].question)
        })

      card.querySelectorAll('input')
        .forEach((input, numAnswer) => {
          const label = input.nextElementSibling
          input.value = questions[numQuestion].answers[numAnswer] // escape ?
          input.id = `${numQuestion + 1}_${numAnswer + 1}`
          input.name = `question ${numQuestion + 1}`

          label.for = `${numQuestion}_${numAnswer}`
          label.textContent = decodeHtml(questions[numQuestion].answers[numAnswer])
        })
    })

    button.disabled = false
}
