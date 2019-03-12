import { success, notFound } from '../../services/response/'
import { Noticia } from '.'
import { User } from '../user'
import { Comentario } from '../comentario'

export const create = async ({ user, bodymen: { body } }, res, next) => {
  var nuevaNoticia
  await Noticia.create({ ...body, autor: user })
    .then((noticia) => {
      nuevaNoticia = noticia.id
      console.log(nuevaNoticia)
      return noticia.view(true)
    })
    .then(success(res, 201))
    .catch(next)

  await User.findById(user.id)
    .then(usuario => {
      usuario.noticias.push(nuevaNoticia)
      usuario.save()
    })
    .catch(next)
}

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Noticia.count(query)
    .then(count => Noticia.find(query, select, cursor)
      .then((noticias) => ({
        count,
        rows: noticias.map((noticia) => noticia.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) => {
  return Noticia.findById(params.id)
    .populate({
      path: 'comentarios'
    }).populate('comentarios.autor')
    .then(noticia => noticia.view(true))
    .then(success(res))
    .catch(next)
}
export const update = ({ bodymen: { body }, params }, res, next) =>
  Noticia.findById(params.id)
    .then(notFound(res))
    .then((noticia) => noticia ? Object.assign(noticia, body).save() : null)
    .then((noticia) => noticia ? noticia.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Noticia.findById(params.id)
    .then(notFound(res))
    .then((noticia) => noticia ? noticia.remove() : null)
    .then(success(res, 204))
    .catch(next)
