import { success, notFound } from '../../services/response/'
import { Comentario } from '.'
import { Noticia } from '../noticia'

export const create = async ({ user, bodymen: { body }, params }, res, next) => {
  var comentarioId
  await Comentario.create(body)
    .then((comentario) => {
      comentario.autor = user
      comentario.autor.id = user.id
      comentarioId = comentario.id
      comentario.save()
      return comentario.view(true)
    })
    .then(success(res, 201))
    .catch(next)

  await Noticia.findById(params.noticia)
    .then(noticia => {
      noticia.comentarios.push(comentarioId)
      noticia.save()
    })
    .catch(next)
}
export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Comentario.count(query)
    .then(count => Comentario.find(query, select, cursor)
      .then((comentarios) => ({
        count,
        rows: comentarios.map((comentario) => comentario.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Comentario.findById(params.id)
    .then(notFound(res))
    .then((comentario) => comentario ? comentario.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Comentario.findById(params.id)
    .then(notFound(res))
    .then((comentario) => comentario ? Object.assign(comentario, body).save() : null)
    .then((comentario) => comentario ? comentario.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ user, params }, res, next) => {
  Comentario.findById(params.id)
    .then(notFound(res))
    .then((comentario) => {
      if (comentario.autor.id.equals(user.id)) {
        return comentario.remove()
      } else {
        return next
      }
    })
    .then(success(res, 204))
    .catch(next)
}

export const destroyAdmin = ({ params }, res, next) => {
  Comentario.findById(params.id)
    .then(notFound(res))
    .then((comentario) => comentario ? comentario.remove() : null)
    .then(success(res, 204))
    .catch(next)
}
