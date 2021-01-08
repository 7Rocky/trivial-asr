let animation

const startLoading = () => {
  const button = document.getElementById('dropdown-languages')
  button.disabled = true

  removeAudios()

  document.querySelectorAll('.fa-volume-up')
    .forEach(i => i.parentNode.disabled = true)

  document.querySelectorAll('h4 .question')
    .forEach(span => {
      span.innerHTML = '<div class="ms-3 spinner-border" role="status"><span class="visually-hidden">Loading...</span></div>'
    })

  document.querySelectorAll('label')
    .forEach(label => {
      label.innerHTML = '&nbsp;'
      label.classList.add('loading-answers')
    })

  animation = setInterval(() => {
    document.querySelectorAll('label')
      .forEach(label => label.style.width = 80 * Math.random() + '%')
  }, 500)
}

const stopLoading = () => {
  clearInterval(animation)

  const button = document.getElementById('dropdown-languages')
  button.disabled = false

  document.querySelectorAll('.fa-volume-up').forEach(i => i.parentNode.disabled = false)

  document.querySelectorAll('label')
    .forEach(label => {
      label.style.width = ''
      label.classList.remove('loading-answers')
    })
}

const putAudio = button => {
  const parent = button.parentElement
  const number = parent.querySelector('.question-number').textContent
  const audio = new Audio(`audio?q=${number - 1}&v=${Math.random()}`)

  audio.controls = true
  audio.autoplay = true

  audio.addEventListener('error', () => {
    const modal = new bootstrap.Modal(document.getElementById('text-to-speech-error'), {
      keyboard: false
    })

    modal.show()
  })

  parent.querySelector('h4').after(audio)
  parent.removeChild(parent.querySelector('button'))
}

const removeAudios = () => {
  document.querySelectorAll('audio')
    .forEach(audio => {
      audio.parentNode.insertAdjacentHTML('afterend', '<button class="align-self-center btn d-flex text-white" onclick="putAudio(this)"><i class="fas fa-volume-up h1"></i></button>')
      audio.parentNode.removeChild(audio)
    })
}

const decodeHtml = html => {
  let textarea = document.createElement('textarea')
  textarea.innerHTML = html

  return textarea.value
}

const selectLanguage = language => {
  const button = document.getElementById('dropdown-languages')
  const ul = button.nextElementSibling

  button.textContent = language

  ul.querySelectorAll('a')
    .forEach(a => {
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
  const language = a.textContent
  selectLanguage(language)

  startLoading()

  const id = a.id
  const questions = await getQuestions(id)

  stopLoading()

  renderQuiz(questions) 
}

const getQuestions = async id => {
  const res = await fetch(`questions?language=${id}`)
  const questions = await res.json()

  return questions
}

const renderQuiz = questions => {
  document.querySelectorAll('.card-body')
    .forEach((card, numQuestion) => {
      card.querySelectorAll('h4 .question')
        .forEach(span => span.textContent = decodeHtml(questions[numQuestion].question))

      card.querySelectorAll('input')
        .forEach((input, numAnswer) => {
          const label = input.nextElementSibling
          input.value = questions[numQuestion].answers[numAnswer]
          input.id = `${numQuestion + 1}_${numAnswer + 1}`
          input.name = `question ${numQuestion + 1}`

          label.for = `${numQuestion}_${numAnswer}`
          label.textContent = decodeHtml(questions[numQuestion].answers[numAnswer])
        })
    })
}

const getUser = async () => {
  const res = await fetch("user")

  if (res.status === 200) {
    const user = await res.json()

    if (user.picture && user.givenName) {
      document.querySelector('#dropdown-user img').src = user.picture
      document.querySelector('#dropdown-user span').textContent = user.givenName
    }
  }
}
