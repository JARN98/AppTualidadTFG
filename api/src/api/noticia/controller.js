import { success, notFound } from '../../services/response/'
import { Noticia } from '.'
import { User } from '../user'

export const create = ({bodymen: { body }, user}, res, next) => {
  Noticia.create(body)
    .then((noticia) => {
      console.log(user);
      // return noticia.view(true);
    })
    .then(success(res, 201))
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

export const show = ({ params }, res, next) =>
  Noticia.findById(params.id)
    .then(notFound(res))
    .then((noticia) => noticia ? noticia.view(true) : null)
    .then(success(res))
    .catch(next)

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
