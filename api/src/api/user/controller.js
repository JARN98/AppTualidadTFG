import { success, notFound } from '../../services/response/'
import { User } from '.'
import { sign } from '../../services/jwt'
import { Noticia } from '../noticia'

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  User.count(query)
    .then(count => User.find(query, select, cursor)
      .then(users => ({
        rows: users.map((user) => user.view()),
        count
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  User.findById(params.id)
    .then(notFound(res))
    .then((user) => user ? user.view() : null)
    .then(success(res))
    .catch(next)

export const showMe = ({ user }, res, next) =>
  User.findById(user.id)
    .then(notFound(res))
    .then((user) => user ? user.view(true) : null)
    .then(success(res))
    .catch(next)

export const create = ({ bodymen: { body } }, res, next) =>
  User.create(body)
    .then(user => {
      sign(user.id)
        .then((token) => ({ token, user: user.view(true) }))
        .then(success(res, 201))
    })
    .catch((err) => {
      /* istanbul ignore else */
      if (err.name === 'MongoError' && err.code === 11000) {
        res.status(409).json({
          valid: false,
          param: 'email',
          message: 'email already registered'
        })
      } else {
        next(err)
      }
    })

export const addFav = ({ bodymen: { body }, params, user }, res, next) => {
  var encontrado = false;
  var i = 0

  while (!encontrado && i < user.favs.length && user.favs.length != 0) {

    if (user.favs[i].equals(params.noticia)) {
      encontrado = true
    } else {
      i = i + 1
    }

  }

  if (encontrado) {
    var ya = false
    var j = 0
    while (!ya) {
      console.log('hola');

      if (user.favs[j].equals(params.noticia)) {
        ya = true
      } else {
        j = j + 1
      }
    }
  }

  if (ya) {
    user.favs.splice(j, 1)
    user.save();


    Noticia.findById(params.noticia)
      .then(noticia => {
        var numero = parseInt(noticia.likes)
        numero = numero - 1
        var caracteres = numero + ''
        noticia.likes = caracteres
        noticia.save()
        return noticia.view()
      })
      .then(success(res))
      .catch(next)
  } else {
    user.favs.push(params.noticia)
    user.save()

    Noticia.findById(params.noticia)
      .then(noticia => {
        var numero = parseInt(noticia.likes)
        numero = numero + 1
        var caracteres = numero + ''
        noticia.likes = caracteres
        noticia.save()
        return noticia.view()
      })
      .then(success(res))
      .catch(next)
  }

  res.send(user)
}

export const update = ({ bodymen: { body }, params, user }, res, next) =>
  User.findById(params.id === 'me' ? user.id : params.id)
    .then(notFound(res))
    .then((result) => {
      if (!result) return null
      const isAdmin = user.role === 'admin'
      const isSelfUpdate = user.id === result.id
      if (!isSelfUpdate && !isAdmin) {
        res.status(401).json({
          valid: false,
          message: 'You can\'t change other user\'s data'
        })
        return null
      }
      return result
    })
    .then((user) => user ? Object.assign(user, body).save() : null)
    .then((user) => user ? user.view(true) : null)
    .then(success(res))
    .catch(next)

export const updatePassword = ({ bodymen: { body }, params, user }, res, next) =>
  User.findById(params.id === 'me' ? user.id : params.id)
    .then(notFound(res))
    .then((result) => {
      if (!result) return null
      const isSelfUpdate = user.id === result.id
      if (!isSelfUpdate) {
        res.status(401).json({
          valid: false,
          param: 'password',
          message: 'You can\'t change other user\'s password'
        })
        return null
      }
      return result
    })
    .then((user) => user ? user.set({ password: body.password }).save() : null)
    .then((user) => user ? user.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  User.findById(params.id)
    .then(notFound(res))
    .then((user) => user ? user.remove() : null)
    .then(success(res, 204))
    .catch(next)
